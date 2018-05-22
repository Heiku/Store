package com.heiku.snacks.dao;

import com.heiku.snacks.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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


    /**
     * 分页查询店铺
     *
     * @param shopCondition
     * @param rowIndex  指定行数查询
     * @param pageSize  返回条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);


    /**
     * 返回店铺总数
     *
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);
}
