package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class UserDaoTest extends BaseTest {


    @Autowired
    private UserDao userDao;


    @Test
    public void insertUser() {
        User user = new User();
        user.setName("KaKa");
        user.setEmail("1132825187@qq.com");
        user.setGender("1");
        user.setUserType(1);
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        user.setEnableStatus(1);

        int num = userDao.insertUser(user);
        assertEquals(1, num);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setUserId(4L);
        user.setName("KAKA");

        int num = userDao.updateUser(user);
        assertEquals(1, num);

    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void queryUserById() {
        String name = userDao.queryUserById(4L).getName();
        assertEquals("KAKA", name);
    }

    @Test
    public void queryUserList() {
        User user = new User();
        user.setEnableStatus(1);
        user.setUserType(1);

        List<User> userList = userDao.queryUserList(user, 0, 5);
        assertEquals(4, userList.size());
    }

    @Test
    public void queryUserCount() {

        User user = new User();
        user.setEnableStatus(1);
        user.setUserType(1);

        int count = userDao.queryUserCount(user);
        assertEquals(4, count);
    }
}