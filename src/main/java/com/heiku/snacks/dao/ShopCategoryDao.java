package com.heiku.snacks.dao;

import com.heiku.snacks.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {

    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategory);

    /**
     * 通过id查询商品类别
     *
     * @param shopCategoryId
     * @return
     */
    ShopCategory queryShopCategoryById(Long shopCategoryId);


    /**
     * 通过id组拆线呢商品类别列表
     *
     * @param shopCategoryIdList
     * @return
     */
    List<ShopCategory> queryShopCategoryByIds(List<Long> shopCategoryIdList);

    /**
     * 插入商品类别信息
     *
     * @param shopCategory
     * @return
     */
    int insertShopCategory(ShopCategory shopCategory);

    /**
     * 更新商品类别信息
     *
     * @param shopCategory
     * @return
     */
    int updateShopCategory(ShopCategory shopCategory);

    /**
     * 删除商品类别信息
     *
     * @param shopCategoryId
     * @return
     */
    int deleteShopCategory(Long shopCategoryId);
}
