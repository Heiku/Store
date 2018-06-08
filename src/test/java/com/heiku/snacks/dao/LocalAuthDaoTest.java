package com.heiku.snacks.dao;

import com.heiku.snacks.BaseTest;
import com.heiku.snacks.entity.LocalAuth;
import com.heiku.snacks.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void insertLocalAuth() {
        User user = new User();
        user.setUserId(2L);

        LocalAuth localAuth = new LocalAuth();
        localAuth.setUser(user);

        localAuth.setUserName("Jame Li");
        localAuth.setPassword("maimaidedie");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());

        int effectNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectNum);

    }

    @Test
    public void updateLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setLocalAuthId(1L);
        localAuth.setPassword("lilililili");

        int effectNum = localAuthDao.updateLocalAuth(2L, "Jame Li", "maimaidedie", "lilililili", new Date());
        assertEquals(1, effectNum);
    }

    @Test
    public void queryLocalAuthById() {
        LocalAuth localAuth = localAuthDao.queryLocalAuthById(2L);
        assertEquals("Jame Li", localAuth.getUserName());
    }

    @Test
    public void checkLocalAuth() {
        LocalAuth localAuth = localAuthDao.checkLocalAuth("Jame Li", "lilililili");
        System.out.println(localAuth.getUser().getName());
    }
}