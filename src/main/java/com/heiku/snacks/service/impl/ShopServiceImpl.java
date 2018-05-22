package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.ShopDao;
import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.enums.ShopStateEnum;
import com.heiku.snacks.exception.ShopOperationException;
import com.heiku.snacks.service.ShopService;
import com.heiku.snacks.util.ImageUtil;
import com.heiku.snacks.util.PageCalculator;
import com.heiku.snacks.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;


    @Override
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


    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException {

        // 图片处理
        if (shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
                if (shopImg != null){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImg);
                }
                // 更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else{
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error: " + e.getMessage());
            }
        }
    }


    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        int rowIndex= PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);

        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();

        if (shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }
}
