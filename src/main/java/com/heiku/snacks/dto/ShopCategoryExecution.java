package com.heiku.snacks.dto;

import com.heiku.snacks.entity.ShopCategory;

import java.util.List;

public class ShopCategoryExecution {

    private int state;

    private String stateInfo;

    private ShopCategory shopCategory;

    private List<ShopCategory> shopCategoryList;

    public ShopCategoryExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public ShopCategoryExecution(int state, ShopCategory shopCategory) {
        this.state = state;
        this.shopCategory = shopCategory;
    }

    public ShopCategoryExecution(int state, List<ShopCategory> shopCategoryList) {
        this.state = state;
        this.shopCategoryList = shopCategoryList;
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

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }
}
