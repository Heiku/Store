package com.heiku.snacks.service;

import com.heiku.snacks.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    /**
     * 获取商品类别列表
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
