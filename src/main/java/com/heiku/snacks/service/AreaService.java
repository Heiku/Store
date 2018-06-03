package com.heiku.snacks.service;

import com.heiku.snacks.dto.AreaExecution;
import com.heiku.snacks.entity.Area;

import java.util.List;


public interface AreaService {

    /**
     * 获取区域列表
     *
     * @return
     */
    List<Area> getAreaList();

    /**
     * 添加区域信息
     *
     * @param area
     * @return
     */
    AreaExecution addArea(Area area);

    /**
     * 修改区域信息
     *
     * @param area
     * @return
     */
    AreaExecution modifyArea(Area area);

    /**
     * 移除区域信息
     *
     * @param areaId
     * @return
     */
    AreaExecution removeArea(Long areaId);

    /**
     *  移除多个区域信息
     * @param areaIdList
     * @return
     */
    AreaExecution removeAreaList(List<Long> areaIdList);


}
