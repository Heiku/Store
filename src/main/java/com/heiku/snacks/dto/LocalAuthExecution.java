package com.heiku.snacks.dto;

import com.heiku.snacks.entity.LocalAuth;

import java.util.List;

public class LocalAuthExecution {

    private int state;

    private String stateInfo;

    private int count;

    private LocalAuth localAuth;

    private List<LocalAuth> localAuthList;

    public LocalAuthExecution() {
    }

    public LocalAuthExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public LocalAuthExecution(int state, LocalAuth localAuth) {
        this.state = state;
        this.localAuth = localAuth;
    }

    public LocalAuthExecution(int state, List<LocalAuth> localAuthList) {
        this.state = state;
        this.localAuthList = localAuthList;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public int getCount() {
        return count;
    }

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public List<LocalAuth> getLocalAuthList() {
        return localAuthList;
    }
}
