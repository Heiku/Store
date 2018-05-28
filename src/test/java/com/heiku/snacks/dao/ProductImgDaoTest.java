package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testBatchInsertProductImg() {

        ProductImg img1 = new ProductImg();
        img1.setImgAddr("test1 address");
        img1.setImgDesc("test1 desc");
        img1.setPriority(2);
        img1.setProductId(2L);
        img1.setCreateTime(new Date());

        ProductImg img2 = new ProductImg();
        img2.setImgAddr("test2 address");
        img2.setImgDesc("test2 desc");
        img2.setPriority(1);
        img2.setProductId(2L);
        img2.setCreateTime(new Date());

        ProductImg img3 = new ProductImg();
        img3.setImgAddr("test3 address");
        img3.setImgDesc("test3 desc");
        img3.setPriority(3);
        img3.setProductId(2L);
        img3.setCreateTime(new Date());

        List<ProductImg> list = new ArrayList<>();
        list.add(img1);
        list.add(img2);
        list.add(img3);

        int effectNum = productImgDao.batchInsertProductImg(list);
        assertEquals(3, effectNum);
    }


    @Test
    public void testDeleteProductImg(){
        Long productId = 2L;
        int effectNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(3, effectNum);
    }


    @Test
    public void testQueryProductImgList(){
        Long productId = 4L;
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        assertEquals(2, productImgList.size());
    }
}