package com.heiku.snacks.service;

import com.heiku.snacks.dto.ShopCategoryExecution;
import com.heiku.snacks.entity.ShopCategory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ShopCategoryService {

    /**
     * 获取商品类别列表
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);




    /**
     * 添加商品分类
     *
     * @param shopCategory
     * @param thumbnail
     * @return
     */
    ShopCategoryExecution addShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail);


    /**
     * 修改商品类别
     *
     * @param shopCategory
     * @param thumbnail
     * @param change
     * @return
     */
    ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail, boolean change);


    /**
     * 删除商品类别信息
     *
     * @param shopCategoryId
     * @return
     */
    ShopCategoryExecution removeShopCategory(Long shopCategoryId);
}
