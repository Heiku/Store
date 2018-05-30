package com.heiku.snacks.service;

import com.heiku.snacks.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {

    /**
     * 获取头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
