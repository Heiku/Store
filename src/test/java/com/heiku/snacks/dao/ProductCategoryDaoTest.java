package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class ProductCategoryDaoTest extends BaseTest {


    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void queryProductCategoryList() {
        long shopId = 6L;
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
        assertEquals(4, list.size());
    }

    @Test
    public void testInsertBatchProductCategory(){
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setCreateTime(new Date());
        productCategory1.setPriority(2);
        productCategory1.setShopId(5L);
        productCategory1.setProductCategoryName("鸡柳饭类");

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCreateTime(new Date());
        productCategory2.setPriority(3);
        productCategory2.setShopId(5L);
        productCategory2.setProductCategoryName("紫菜肉卷类");

        ProductCategory productCategory3 = new ProductCategory();
        productCategory3.setCreateTime(new Date());
        productCategory3.setPriority(6);
        productCategory3.setShopId(5L);
        productCategory3.setProductCategoryName("酿香菇类");

        ProductCategory productCategory4 = new ProductCategory();
        productCategory4.setCreateTime(new Date());
        productCategory4.setPriority(1);
        productCategory4.setShopId(5L);
        productCategory4.setProductCategoryName("煎酿三宝类");

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        productCategoryList.add(productCategory3);
        productCategoryList.add(productCategory4);

        int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(4, effectNum);
    }


    @Test
    public void testDeleteProductCategory(){
        Long shopId = 5L;
        int effectNum = productCategoryDao.deleteProductCategory(5, shopId);
        assertEquals(1, effectNum);
    }
}