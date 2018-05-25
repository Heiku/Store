package com.heiku.snacks.dto;

import com.heiku.snacks.entity.ProductCategory;
import com.heiku.snacks.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {

    // 状态代码
    private int state;

    // 状态信息
    private String stateInfo;


    // 返回列表
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    // fail
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // success
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> list){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = list;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
