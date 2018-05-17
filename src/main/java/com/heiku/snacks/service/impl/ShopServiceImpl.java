package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.ShopDao;
import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.enums.ShopStateEnum;
import com.heiku.snacks.exception.ShopOperationException;
import com.heiku.snacks.service.ShopService;
import com.heiku.snacks.util.ImageUtil;
import com.heiku.snacks.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;


    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            int effectNum = shopDao.insertShop(shop);

            // fail,使用runtimeTimeException 回滚
            if (effectNum <= 0){
                throw new ShopOperationException("店铺创建失败！");
            }else {
                if (shopImg != null){
                    // 存储图片
                    try {
                        addShopImg(shop, shopImg);

                    } catch (Exception e) {
                        throw new ShopOperationException("add ShopImg Error: " + e.getMessage());
                    }

                    // 更新数据库中的图片地址
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error: " + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }


    private void addShopImg(Shop shop,CommonsMultipartFile shopImg){
        // 获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);

        // 设置图片
        shop.setShopImg(shopImgAddr);

    }
}
