package com.heiku.snacks.enums;

public enum LocalAuthStateEnum {

    SUCCESS(1, "成功"),
    EMPTY(-1002, "信息不完整"),
    INNER_FAIL(-1001, "操作失败"),
    LOGINFAIL(-1, "登录失败"),
    ONLY_ONE_ACCOUNT(-1003, "最多绑定一个账号");

    private int state;

    private String stateInfo;

    LocalAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public int getState() {
        return state;
    }

    public static LocalAuthStateEnum stateOf(int index) {
        for (LocalAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
