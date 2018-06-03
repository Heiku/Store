package com.heiku.snacks.dao;

import com.heiku.snacks.entity.Area;

import java.util.List;

public interface AreaDao {


    /**
     * 获取区域列表
     *
     * @return areaList
     */
    List<Area> queryArea();

    /**
     * 插入区域信息
     *
     * @param area
     * @return
     */
    int insertArea(Area area);


    /**
     * 更新区域信息
     *
     * @return
     */
    int updateArea(Area area);


    /**
     * 删除区域信息
     *
     * @return
     */
    int deleteArea(long areaId);


    /**
     *  删除多个区域信息
     *
     * @param areaList
     * @return
     */
    int batchDeleteArea(List<Long> areaList);
}
