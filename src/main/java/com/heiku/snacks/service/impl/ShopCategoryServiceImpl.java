package com.heiku.snacks.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiku.snacks.cache.JedisUtil;
import com.heiku.snacks.dao.ShopCategoryDao;
import com.heiku.snacks.dto.ShopCategoryExecution;
import com.heiku.snacks.entity.ShopCategory;
import com.heiku.snacks.enums.ShopCategoryEnum;
import com.heiku.snacks.service.ShopCategoryService;
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
public class ShopCategoryServiceImpl implements ShopCategoryService {

    private static String SHOPCATEGORYKEY = "shopcategorylist";

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Autowired
    private JedisUtil.Keys jedisKeys;


    /**
     * 获取类别列表
     *
     * @param shopCategoryCondition
     * @return
     */
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {

        String key = SHOPCATEGORYKEY + "_" + shopCategoryCondition.getParent().getShopCategoryId();
        List<ShopCategory> shopCategoryList = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            if (!jedisKeys.exists(key)){
                shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);

                String json = mapper.writeValueAsString(shopCategoryList);
                jedisStrings.set(key, json);
            }else {
                String json = jedisStrings.get(key);

                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);

                shopCategoryList = mapper.readValue(json, javaType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shopCategoryList;
    }

    /**
     * 添加类别
     *
     * @param shopCategory
     * @param thumbnail
     * @return
     */
    @Override
    @Transactional
    public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail) {

        if (shopCategory != null){
            shopCategory.setCreateTime(new Date());
            shopCategory.setLastEditTime(new Date());

            // 文件添加
            if (thumbnail != null){
                addThumbnail(shopCategory, thumbnail);
            }

            try {
                int effectNum = shopCategoryDao.insertShopCategory(shopCategory);

                // 删除redis旧信息
                if (effectNum > 0) {
                    String prefix = SHOPCATEGORYKEY;
                    Set<String> keySet = jedisKeys.keys(prefix + "*");
                    for (String key : keySet)
                        jedisKeys.del(key);

                    return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS.getState(), shopCategory);
                }else {
                    return new ShopCategoryExecution(ShopCategoryEnum.INNER_ERROR.getState(),
                            ShopCategoryEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("添加失败：" + e.getMessage());
            }
        }else {
            return new ShopCategoryExecution(ShopCategoryEnum.EMPTY.getState(), ShopCategoryEnum.EMPTY.getStateInfo());
        }
    }


    /**
     *修改类别
     *
     * @param shopCategory
     * @param thumbnail
     * @param change
     * @return
     */
    @Override
    @Transactional
    public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail, boolean change) {
        if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0){
            shopCategory.setLastEditTime(new Date());

            // 文件存储，属性修改
            if (thumbnail != null && change == true){
                ShopCategory temp = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());

                // 删除旧文件
                if (temp.getShopCategoryImg() != null){
                    ImageUtil.deleteFileOrPath(temp.getShopCategoryImg());
                }

                addThumbnail(shopCategory, thumbnail);
            }

            try {
                int effectNum = shopCategoryDao.updateShopCategory(shopCategory);

                if (effectNum > 0){
                    String prefix = SHOPCATEGORYKEY;

                    Set<String> keySet = jedisKeys.keys(prefix + "*");
                    for (String key : keySet)
                        jedisKeys.del(key);

                    return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS.getState(), shopCategory);
                }else {
                    return new ShopCategoryExecution(ShopCategoryEnum.INNER_ERROR.getState(),
                            ShopCategoryEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("修改失败：" + e.getMessage());
            }
        }else {
            return new ShopCategoryExecution(ShopCategoryEnum.EMPTY.getState(), ShopCategoryEnum.EMPTY.getStateInfo());
        }
    }

    @Override
    public ShopCategoryExecution removeShopCategory(Long shopCategoryId) {
        if (shopCategoryId > 0){
            try {
                ShopCategory temp = shopCategoryDao.queryShopCategoryById(shopCategoryId);

                if (temp.getShopCategoryImg() != null){
                    ImageUtil.deleteFileOrPath(temp.getShopCategoryImg());
                }

                int effectNum = shopCategoryDao.deleteShopCategory(shopCategoryId);

                if (effectNum > 0){
                    String prefix = SHOPCATEGORYKEY;

                    Set<String> keySet = jedisKeys.keys(prefix + "*");
                    for (String key : keySet)
                        jedisKeys.del(key);

                    return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS.getState(), temp);

                }else {
                    return new ShopCategoryExecution(ShopCategoryEnum.INNER_ERROR.getState(),
                            ShopCategoryEnum.INNER_ERROR.getStateInfo());
                }
            }catch (Exception e){
                throw new RuntimeException("删除失败：" + e.getMessage());
            }
        }else {
            return new ShopCategoryExecution(ShopCategoryEnum.EMPTY.getState(),
                    ShopCategoryEnum.EMPTY.getStateInfo());
        }
    }

    private void addThumbnail(ShopCategory shopCategory, CommonsMultipartFile thumbnail){
        String destion = PathUtil.getImgBasePath();
        String thumbnailAddress = ImageUtil.generateNormalImg(thumbnail, destion);
        shopCategory.setShopCategoryImg(thumbnailAddress);
    }
}
