package com.heiku.snacks.service;

import com.heiku.snacks.dto.HeadLineExecution;
import com.heiku.snacks.entity.HeadLine;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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


    /**
     * 添加头条
     *
     * @param headLine
     * @param thumbnail
     * @return
     */
    HeadLineExecution addHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail);


    /**
     * 修改头条
     *
     * @param headLine
     * @param thumbnail
     * @return
     */
    HeadLineExecution modifyHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail);


    /**
     * 删除头条
     *
     * @param headLineId
     * @return
     */
    HeadLineExecution removeHeadLine(Long headLineId);


}
