package com.heiku.snacks.dto;

import com.heiku.snacks.entity.Area;

import java.util.List;

public class AreaExecution {

    private int state;

    private String stateInfo;

    private int count;

    private Area area;

    private List<Area> areaList;

    public AreaExecution() {
    }

    // fail
    public AreaExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    // success
    public AreaExecution(int state, String stateInfo, Area area) {
        this.state = state;
        this.stateInfo = stateInfo;
        this.area = area;
    }

    // success

    public AreaExecution(int state, String stateInfo, List<Area> areaList) {
        this.state = state;
        this.stateInfo = stateInfo;
        this.areaList = areaList;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
