package com.heiku.snacks.exception;

import com.heiku.snacks.entity.ProductCategory;

public class ProductCategoryException extends RuntimeException{

    public ProductCategoryException(String msg){
        super(msg);
    }
}
