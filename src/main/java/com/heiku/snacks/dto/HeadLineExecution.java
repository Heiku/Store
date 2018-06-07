package com.heiku.snacks.dto;

import com.heiku.snacks.entity.HeadLine;

import java.util.List;

public class HeadLineExecution {

    private int state;

    private String stateInfo;

    private HeadLine headLine;

    private List<HeadLine> headLineList;

    public HeadLineExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public HeadLineExecution(int state, HeadLine headLine) {
        this.state = state;
        this.headLine = headLine;
    }

    public HeadLineExecution(int state, List<HeadLine> headLineList) {
        this.state = state;
        this.headLineList = headLineList;
    }
}
