package com.heiku.snacks.dao;

import com.heiku.snacks.entity.Area;

import java.util.List;

public interface AreaDao {


    /**
     * get all Area
     * @return areaList
     */
    List<Area> queryArea();
}
