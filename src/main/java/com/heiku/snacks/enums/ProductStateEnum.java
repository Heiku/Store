package com.heiku.snacks.enums;

public enum ProductStateEnum {

    SUCCESS(0, "操作成功"),
    PASS(1, "通过认证"),
    OFFLINE(-1, "未批准商品"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY(-1002, "商品信息不完整");

    private int state;

    private String stateInfo;

    ProductStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductStateEnum stateOf(int index){
        for (ProductStateEnum state : values())
            if (state.getState() == index)
                return state;

        return null;
    }
}
