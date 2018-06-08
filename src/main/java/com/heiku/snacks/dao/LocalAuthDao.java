package com.heiku.snacks.dao;

import com.heiku.snacks.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LocalAuthDao {

    /**
     * insert
     *
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);


    /**
     * update
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    int updateLocalAuth(@Param("userId")Long userId, @Param("userName")String userName,
                        @Param("password") String password, @Param("newPassword")String newPassword,
                        @Param("lastEditTime")Date lastEditTime);


    /**
     * id 查询
     *
     * @param userId
     * @return
     */
    LocalAuth queryLocalAuthById(@Param("userId") long userId);


    /**
     * username password 检验
     *
     * @param userName
     * @param password
     * @return
     */
    LocalAuth checkLocalAuth(@Param("userName") String userName, @Param("password") String password);

}
