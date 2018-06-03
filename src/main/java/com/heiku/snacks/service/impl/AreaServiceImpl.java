package com.heiku.snacks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiku.snacks.cache.JedisUtil;
import com.heiku.snacks.dao.AreaDao;
import com.heiku.snacks.dto.AreaExecution;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.enums.AreaStateEnum;
import com.heiku.snacks.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static String AREALISTKEY = "arealist";

    /**
     * 获取区域列表，如果redis中没有，就从数据库中查找，再将新区域列表存入redis中
     *
     * @return
     */
    @Override
    public List<Area> getAreaList() {

        String key = AREALISTKEY;
        List<Area> areaList = null;

        ObjectMapper mapper = new ObjectMapper();

        // redis中不存在，到数据库中查找
        if (!jedisKeys.exists(key)){
            areaList = areaDao.queryArea();

            // 转化为json，存储到redis
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedisStrings.set(key, jsonString);
        }else {

            // 在redis中存在，将json转化为list
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);

            try {
                areaList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return areaList;
    }

    /**
     * 更新db中的区域信息，删除redis中的旧区域列表
     * @param area
     * @return
     */
    @Override
    @Transactional
    public AreaExecution addArea(Area area) {
        if (area.getAreaName() != null && !"".equals(area.getAreaName())){
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());

            try {
                int effectNum = areaDao.insertArea(area);

                // 添加成功，删除redis中的旧信息
                if (effectNum > 0){
                    String key = AREALISTKEY;
                    if (jedisKeys.exists(key))
                        jedisKeys.del(key);

                    return new AreaExecution(AreaStateEnum.SUCCESS.getState(),
                            AreaStateEnum.SUCCESS.getStateInfo(),area);
                }else {
                    return new AreaExecution(AreaStateEnum.INNER_ERROR.getState(), AreaStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("添加信息失败：" + e.getMessage());
            }
        }else {
            return new AreaExecution(AreaStateEnum.EMPTY.getState(), AreaStateEnum.INNER_ERROR.getStateInfo());
        }
    }


    // 修改db中的区域信息，删除旧redis区域列表
    @Override
    @Transactional
    public AreaExecution modifyArea(Area area) {
        if (area.getAreaId() != null && area.getAreaId() > 0){
            area.setLastEditTime(new Date());

            try {
                int effectNum = areaDao.updateArea(area);

                // 更新成功，删除redis中的旧信息
                if (effectNum > 0){
                    String key = AREALISTKEY;
                    if (jedisKeys.exists(key))
                        jedisKeys.del(key);

                    return new AreaExecution(AreaStateEnum.SUCCESS.getState(),
                            AreaStateEnum.SUCCESS.getStateInfo(),area);
                }else {
                    return new AreaExecution(AreaStateEnum.INNER_ERROR.getState(), AreaStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("更新信息失败：" + e.getMessage());
            }
        }else {
            return new AreaExecution(AreaStateEnum.EMPTY.getState(), AreaStateEnum.EMPTY.getStateInfo());
        }
    }


    @Override
    public AreaExecution removeArea(Long areaId) {

        if (areaId > 0){
            try {
                int effectNum = areaDao.deleteArea(areaId);

                if (effectNum > 0){
                    String key = AREALISTKEY;
                    if (jedisKeys.exists(key))
                        jedisKeys.del(key);

                    return new AreaExecution(AreaStateEnum.SUCCESS.getState(),
                            AreaStateEnum.SUCCESS.getStateInfo());
                }else {
                    return new AreaExecution(AreaStateEnum.INNER_ERROR.getState(), AreaStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("删除信息失败：" + e.getMessage());
            }
        }else {
            return new AreaExecution(AreaStateEnum.EMPTY.getState(), AreaStateEnum.EMPTY.getStateInfo());
        }
    }

    @Override
    public AreaExecution removeAreaList(List<Long> areaIdList) {
        if (areaIdList != null && areaIdList.size() > 0){
            try {
                int effectNum = areaDao.batchDeleteArea(areaIdList);

                if (effectNum > 0){
                    String key = AREALISTKEY;
                    if (jedisKeys.exists(key))
                        jedisKeys.del(key);

                    return new AreaExecution(AreaStateEnum.SUCCESS.getState(),
                            AreaStateEnum.SUCCESS.getStateInfo());
                }else {
                    return new AreaExecution(AreaStateEnum.INNER_ERROR.getState(), AreaStateEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("删除信息失败：" + e.getMessage());
            }
        }else {
            return new AreaExecution(AreaStateEnum.EMPTY.getState(), AreaStateEnum.EMPTY.getStateInfo());
        }
    }
}
