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


    /**
     * 更新头条状态
     *
     * @param headLine
     * @return
     */
    int updateHeadLine(HeadLine headLine);

    /**
     * 删除头条
     *
     * @param headLineId
     * @return
     */
    int deleteHeadLine(Long headLineId);


    /**
     * 按id查询headline
     *
     * @param headLineId
     * @return
     */
    HeadLine queryHeadLineById(long headLineId);


    /**
     * 按id组查询headline
     *
     * @param headLinkIdList
     * @return
     */
    List<HeadLine> queryHeadLineByIds(List<Long> headLinkIdList);

}
