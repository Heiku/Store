package com.heiku.snacks.service;

import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.exception.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

public interface ShopService {

    /**
     * 新增店铺信息
     *
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg)  throws ShopOperationException;


    /**
     * 根据店铺Id获取店铺信息
     *
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);


    /**
     * 修改店铺信息
     *
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException;
}
