package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;


    @Test
    public void testInsertProduct() {

        Shop shop = new Shop();
        shop.setShopId(6L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(16L);

        Product product = new Product();
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        product.setProductName("可口可乐");
        product.setProductDesc("肥仔开心水");
        product.setNormalPrice("4.0");
        product.setPromotionPrice("3.5");
        product.setPriority(3);
        product.setImgAddr("test");
        product.setShop(shop);
        product.setProductCategory(productCategory);

        int effectNum = productDao.insertProduct(product);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryProductById(){
        Long productId = 4L;
        Product product = productDao.queryProductById(productId);

        System.out.println(product.getProductName());
        System.out.println(product.getShop().getShopId());
        System.out.println(product.getProductCategory().getProductCategoryName());

        List<ProductImg> productImgs = product.getProductImgList();
        assertEquals(2, productImgs.size());

    }
}