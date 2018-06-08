package com.heiku.snacks.dao;

import com.heiku.snacks.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {


    /**
     * 插入用户
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    int updateUser(User user);


    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    int deleteUser(Long userId);


    /**
     * id查找用户
     *
     * @param userId
     * @return
     */
    User queryUserById(Long userId);


    /**
     * 分页查询用户列表
     *
     * @param user
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<User> queryUserList(@Param("userCondition") User user,
                             @Param("rowIndex") int  rowIndex, @Param("pageSize") int pageSize);


    /**
     * 查询用户数目
     *
     * @param userCondition
     * @return
     */
    int queryUserCount(@Param("userCondition") User userCondition);
}
