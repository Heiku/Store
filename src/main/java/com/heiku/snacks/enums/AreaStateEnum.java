package com.heiku.snacks.enums;

public enum AreaStateEnum {
    SUCCESS(0, "成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY(-1002, "区域信息不完整"),
    OFFLINE(-1, "非法区域");

    private int state;

    private String stateInfo;

    AreaStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AreaStateEnum stateOf(int index){
        for (AreaStateEnum state : values()){
            if (state.getState() == index)
                return state;
        }
        return null;
    }
}
