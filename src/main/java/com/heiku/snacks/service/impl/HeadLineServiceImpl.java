package com.heiku.snacks.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiku.snacks.cache.JedisUtil;
import com.heiku.snacks.dao.HeadLineDao;
import com.heiku.snacks.dto.HeadLineExecution;
import com.heiku.snacks.entity.HeadLine;
import com.heiku.snacks.enums.HeadLineStateEnum;
import com.heiku.snacks.service.HeadLineService;
import com.heiku.snacks.util.ImageUtil;
import com.heiku.snacks.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    private static String HEADLISTKEY = "headlinelist";

    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Autowired
    private JedisUtil.Keys jedisKeys;


    /**
     * 获取头条列表
     *
     * @param headLineCondition
     * @return
     */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition){

        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();

        String key = HEADLISTKEY;

        // 设置不同status的头条key
        if (headLineCondition.getEnableStatus() != null){
            key = key + "_" + headLineCondition.getEnableStatus();
        }

        // 不存在，在db中查找
        try {
            if (!jedisKeys.exists(key)){
                headLineList = headLineDao.queryHeadLine(headLineCondition);
                String json = mapper.writeValueAsString(headLineList);

                jedisStrings.set(key, json);
            }else {
                // 存在，redis提取
                String json = jedisStrings.get(key);
                JavaType javaType = mapper.getTypeFactory()
                        .constructParametricType(ArrayList.class, HeadLine.class);

                headLineList = mapper.readValue(json, javaType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headLineList;
    }


    /**
     * 添加头条信息
     *
     * @param headLine
     * @param thumbnail
     * @return
     */
    @Override
    @Transactional
    public HeadLineExecution addHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail) {
        if (headLine != null){
            headLine.setCreateTime(new Date());
            headLine.setLastEditTime(new Date());

            // 文件添加，属性赋值
            if (thumbnail != null){
                addThumbnail(headLine, thumbnail);
            }

            try {
                int effectNum = headLineDao.insertHeadLine(headLine);
                if (effectNum > 0){
                    String prefix = HEADLISTKEY;

                    // 删除redis旧值
                    Set<String> keySet = jedisKeys.keys(prefix + "*");
                    for (String key : keySet){
                        jedisKeys.del(key);
                    }
                    return new HeadLineExecution(HeadLineStateEnum.SUCCSSS.getState(), headLine);
                }else {
                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR.getState(), HeadLineStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("添加头条失败：" + e.getMessage());
            }
        }else {
            return new HeadLineExecution(HeadLineStateEnum.EMPTY.getState(), HeadLineStateEnum.EMPTY.getStateInfo());
        }
    }


    /**
     * 更新头条
     *
     * @param headLine
     * @param thumbnail
     * @return
     */
    @Override
    @Transactional
    public HeadLineExecution modifyHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail) {
        if (headLine.getLineId() != null && headLine.getLineId() > 0){

            headLine.setLastEditTime(new Date());

            if (thumbnail != null){
                HeadLine temp = headLineDao.queryHeadLineById(headLine.getLineId());

                if (temp.getLineImg() != null){
                    ImageUtil.deleteFileOrPath(temp.getLineImg());
                }
                addThumbnail(headLine, thumbnail);
            }

            try {
                int effectNum = headLineDao.updateHeadLine(headLine);

                if (effectNum > 0){
                    String prefix = HEADLISTKEY;

                    // 删除redis旧值
                    Set<String> keySet = jedisKeys.keys(prefix + "*");
                    for (String key : keySet){
                        jedisKeys.del(key);
                    }

                    return new HeadLineExecution(HeadLineStateEnum.SUCCSSS.getState(), headLine);
                }else {
                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR.getState(), HeadLineStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("更新失败：" + e.getMessage());
            }
        }else {
            return new HeadLineExecution(HeadLineStateEnum.EMPTY.getState(), HeadLineStateEnum.EMPTY.getStateInfo());
        }
    }


    /**
     * 删除头条信息
     *
     * @param headLineId
     * @return
     */
    @Override
    @Transactional
    public HeadLineExecution removeHeadLine(Long headLineId) {
        if (headLineId != null && headLineId > 0){
            try {
                HeadLine temp = headLineDao.queryHeadLineById(headLineId);
                if (temp.getLineImg() != null){
                    ImageUtil.deleteFileOrPath(temp.getLineImg());
                }

                int effectNum = headLineDao.deleteHeadLine(headLineId);
                if (effectNum > 0){
                    String prefix = HEADLISTKEY;

                    // 删除redis旧值
                    Set<String> keySet = jedisKeys.keys(prefix + "*");
                    for (String key : keySet){
                        jedisKeys.del(key);
                    }
                    return new HeadLineExecution(HeadLineStateEnum.SUCCSSS.getState(),  temp);
                }else {
                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR.getState(), HeadLineStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("删除失败：" + e.getMessage());
            }
        }else {
            return new HeadLineExecution(HeadLineStateEnum.EMPTY.getState(), HeadLineStateEnum.EMPTY.getStateInfo());
        }
    }

    private void addThumbnail(HeadLine headLine, CommonsMultipartFile thumbnail){
        String destion = PathUtil.getImgBasePath();
        String thumbnailAddress = ImageUtil.generateNormalImg(thumbnail, destion);
        headLine.setLineImg(thumbnailAddress);
    }
}
