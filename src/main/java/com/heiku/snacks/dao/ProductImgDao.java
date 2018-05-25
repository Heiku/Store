package com.heiku.snacks.dao;

import com.heiku.snacks.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 添加商品图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);
}
