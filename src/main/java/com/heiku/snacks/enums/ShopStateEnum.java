package com.heiku.snacks.enums;

public enum  ShopStateEnum {

    CHECK(0, "审核中"),
    OFFLINE(0, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOPID(-1002, "ShopId为空"),
    NULL_SHOP(-1003, "Shop为空");

    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }


    /**
     * 根据指定的 state状态码 返回相应的 state值
     *
     * @param state
     * @return
     */
    public static ShopStateEnum sateOf(int state){
        for (ShopStateEnum shopStateEnum : values()){
            if (shopStateEnum.getState() == state){
                return shopStateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
