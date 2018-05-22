package com.heiku.snacks.service.impl;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.entity.ShopCategory;
import com.heiku.snacks.entity.User;
import com.heiku.snacks.enums.ShopStateEnum;
import com.heiku.snacks.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

public class ShopServiceImplTest extends BaseTest {

    @Autowired
    private ShopService shopService;



    @Test
    public void testAddShop() {
        Shop shop = new Shop();
        User user = new User();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        user.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);


        shop.setArea(area);
        shop.setManager(user);
        shop.setShopCategory(shopCategory);
        shop.setShopName("黄焖鸡米饭");
        shop.setShopDesc("黄焖鸡全球连锁");
        shop.setPhone("1312131241");
        shop.setShopAddress("南门");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");
    }


    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);

        ShopExecution se= shopService.getShopList(shopCondition, 2, 2);
        System.out.println("店铺列表数为：" + se.getShopList().size());
        System.out.println("店铺总数为：" + se.getCount());


    }
}