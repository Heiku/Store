package com.heiku.snacks.service.impl;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.dto.AreaExecution;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AreaServiceImplTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() {
    }

    @Test
    public void testAddArea(){
        Area area = new Area();
        area.setAreaName("开发区");
        area.setPriority(2);

        AreaExecution areaExecution = areaService.addArea(area);
        assertEquals(0, areaExecution.getState());
    }
}