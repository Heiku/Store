package com.heiku.snacks.dao;

import com.heiku.snacks.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    /**
     * 查询头条列表
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine headLineCondition);


    /**
     * 插入头条信息
     *
     * @param headLine
     * @return
     */
    int insertHeadLine(HeadLine headLine);
}
