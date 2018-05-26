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
}
