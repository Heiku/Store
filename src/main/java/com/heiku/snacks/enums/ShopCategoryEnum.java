package com.heiku.snacks.enums;

import com.heiku.snacks.entity.ShopCategory;

public enum  ShopCategoryEnum {

    SUCCESS(1, "成功"),
    INNER_ERROR(-1001,"操作失败"),
    EMPTY(-1002, "信息不完整");


    private int state;

    private String stateInfo;

    ShopCategoryEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopCategoryEnum stateOf(int index){
        for (ShopCategoryEnum stateEnum : values()){
            if (stateEnum.getState() == index)
                return stateEnum;
        }
        return null;
    }
}
