package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.AreaDao;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
