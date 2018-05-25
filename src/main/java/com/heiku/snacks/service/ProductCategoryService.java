package com.heiku.snacks.service;

import com.heiku.snacks.dto.ProductCategoryExecution;
import com.heiku.snacks.entity.ProductCategory;
import com.heiku.snacks.exception.ProductCategoryException;

import java.util.List;

public interface ProductCategoryService {


    /**
     * 查询指定shopId下的所有商品分类
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategory(long shopId);


    /**
     * 添加多个商品分类
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryException;


    /**
     * 删除商品分类
     *
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryException;
}
