package com.heiku.snacks.service;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        assertEquals("街口", areaList.get(0).getAreaName());
    }
}