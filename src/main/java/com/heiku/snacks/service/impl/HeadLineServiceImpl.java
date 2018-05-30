package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.HeadLineDao;
import com.heiku.snacks.entity.HeadLine;
import com.heiku.snacks.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
