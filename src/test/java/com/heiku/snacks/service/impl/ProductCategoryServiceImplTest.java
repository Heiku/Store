package com.heiku.snacks.service.impl;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.dao.ProductCategoryDao;
import com.heiku.snacks.dto.ProductCategoryExecution;
import com.heiku.snacks.entity.ProductCategory;
import com.heiku.snacks.service.ProductCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryServiceImplTest extends BaseTest {

    @Autowired
    private ProductCategoryService service;

    @Test
    public void batchAddProductCategory() {

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setCreateTime(new Date());
        productCategory1.setPriority(2);
        productCategory1.setShopId(1L);
        productCategory1.setProductCategoryName("奥尔良鸡腿饭类");

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCreateTime(new Date());
        productCategory2.setPriority(3);
        productCategory2.setShopId(1L);
        productCategory2.setProductCategoryName("肉丸类");

        ProductCategory productCategory3 = new ProductCategory();
        productCategory3.setCreateTime(new Date());
        productCategory3.setPriority(3);
        productCategory3.setShopId(1L);
        productCategory3.setProductCategoryName("冬菇鸡类");

        List<ProductCategory> list = new ArrayList<>();

        list.add(productCategory1);
        list.add(productCategory2);
        list.add(productCategory3);

        ProductCategoryExecution execution = service.batchAddProductCategory(list);
        assertEquals(execution.getState() , 1);
    }
}