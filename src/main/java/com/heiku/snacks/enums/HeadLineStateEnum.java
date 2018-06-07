package com.heiku.snacks.enums;

public enum  HeadLineStateEnum {
    SUCCSSS(0, "创建成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY(-1002, "头条信息不完整");

    private int state;

    private String stateInfo;

    HeadLineStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static HeadLineStateEnum stateOf(int index){
        for (HeadLineStateEnum stateEnum : values())
            if (stateEnum.getState() == index)
                return stateEnum;

        return null;
    }
}
