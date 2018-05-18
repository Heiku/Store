package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());


        assertEquals(1, shopCategoryList.size());
        System.out.println(shopCategoryList.get(0).getShopCategoryName());
    }
}