package com.heiku.snacks.enums;

public enum  ProductCategoryStateEnum {

    SUCCESS(1, "创建成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY_LIST(-1002, "列表数小于1");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int index){
        for (ProductCategoryStateEnum stateEnum : values()){
            if (stateEnum.getState() == index)
                return stateEnum;
        }
        return null;
    }

}
