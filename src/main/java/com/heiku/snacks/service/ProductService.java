package com.heiku.snacks.service;

import com.heiku.snacks.dto.ProductExecution;
import com.heiku.snacks.entity.Product;
import com.heiku.snacks.exception.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {

    /**
     * 添加商品并处理图片
     *
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail,
                                List<CommonsMultipartFile> productImgs) throws ProductOperationException;


    /**
     * 查询商品通过Id
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);


    /**
     * 修改商品并处理图片
     *
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
                                   List<CommonsMultipartFile> productImgs) throws ProductOperationException;


    /**
     * 分页获取指定商品属性的商品列表
     *
     * @param product
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product product, int pageIndex, int pageSize);
}
