package com.heiku.snacks.dao;

import com.heiku.snacks.entity.Shop;

public interface ShopDao {

    /**
     * insert shop
     *
     * @param shop
     * @return int
     */
    int insertShop(Shop shop);


    /**
     * update shop
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);


    /**
     * query shop By ID
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);
}
