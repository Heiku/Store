package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testInsertHeadLine(){
        HeadLine line1 = new HeadLine();
        line1.setLineName("testLine4");
        line1.setLineLink("http://../store/testLine4");
        line1.setLineImg("/upload/images/headline/testLine4");
        line1.setPriority(1);
        line1.setCreateTime(new Date());
        line1.setLastEditTime(new Date());
        line1.setEnableStatus(1);

        int effectNum = headLineDao.insertHeadLine(line1);
        assertEquals(1, effectNum);
    }

    @Test
    public void queryHeadLine() {
        HeadLine headLine = new HeadLine();

        List<HeadLine> headLines = headLineDao.queryHeadLine(headLine);
        assertEquals(4, headLines.size());
    }

    @Test
    public void testQueryHeadLineById(){
        Long headLineId = 2L;
        HeadLine headLine = headLineDao.queryHeadLineById(headLineId);
        System.out.println(headLine.getLineName());
    }


    @Test
    public void testUpdateHeadLine(){
        HeadLine headLine = new HeadLine();
        headLine.setLineId(2L);
        headLine.setLineName("new HeadLine Name");
        headLine.setLastEditTime(new Date());

        int effectNum = headLineDao.updateHeadLine(headLine);
        assertEquals(1, effectNum);
    }


    @Test
    public void testQueryHeadLineByIds(){
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        longs.add(2L);

        List<HeadLine> headLineList = headLineDao.queryHeadLineByIds(longs);
        assertEquals(2, headLineList.size());
    }

}