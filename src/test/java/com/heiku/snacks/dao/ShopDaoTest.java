package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.entity.ShopCategory;
import com.heiku.snacks.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        User user = new User();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        user.setUserId(1L);
        area.setAreaId(4);
        shopCategory.setShopCategoryId(1L);

        shop.setArea(area);
        shop.setManager(user);
        shop.setShopCategory(shopCategory);
        shop.setShopName("美味笼仔");
        shop.setShopDesc("蒸饭");
        shop.setPhone("1312131241");
        shop.setShopAddress("正门");
        shop.setEnableStatus(1);
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");

        int num = shopDao.insertShop(shop);
        assertEquals(1, num);
    }


    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();


        shop.setShopId(1L);
        shop.setShopName("娇姐美食");
        shop.setShopDesc("蒸饭");
        shop.setPhone("1312131241");
        shop.setShopAddress("南门");
        shop.setEnableStatus(1);
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");

        int num = shopDao.updateShop(shop);
        assertEquals(1, num);
    }


    @Test
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId: " + shop.getArea().getAreaId());
        System.out.println("areaName: " + shop.getArea().getAreaName());
    }
}