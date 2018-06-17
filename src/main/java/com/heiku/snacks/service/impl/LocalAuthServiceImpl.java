package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.LocalAuthDao;
import com.heiku.snacks.dao.UserDao;
import com.heiku.snacks.dto.LocalAuthExecution;
import com.heiku.snacks.entity.LocalAuth;
import com.heiku.snacks.entity.User;
import com.heiku.snacks.enums.LocalAuthStateEnum;
import com.heiku.snacks.service.LocalAuthService;
import com.heiku.snacks.util.ImageUtil;
import com.heiku.snacks.util.MD5Util;
import com.heiku.snacks.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Autowired
    private UserDao userDao;
    /**
     * 检查登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public LocalAuth checkLocalAuth(String userName, String password) {
        return localAuthDao.checkLocalAuth(userName, password);
    }


    /**
     * id查找账号信息
     *
     * @param userId
     * @return
     */
    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalAuthById(userId);
    }


    /**
     * 账号注册，用户插入
     *
     * @param localAuth
     * @param profileImg
     * @return
     */
    @Override
    @Transactional
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) {
        if (localAuth == null || localAuth.getUserName() == null || localAuth.getPassword() == null){
            return new LocalAuthExecution(LocalAuthStateEnum.EMPTY.getState(), LocalAuthStateEnum.EMPTY.getStateInfo());
        }

        try {
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());

            // 用户密码 md5 加密处理
            localAuth.setPassword(MD5Util.getMD5(localAuth.getPassword()));

            // 图片处理
            if (profileImg != null){
                localAuth.getUser().setCreateTime(new Date());
                localAuth.getUser().setLastEditTime(new Date());
                localAuth.getUser().setEnableStatus(1);

                try {
                    addProfileImg(localAuth, profileImg);
                }catch (Exception e){
                    throw new RuntimeException("error:" + e.getMessage());
                }
            }


            // 先添加用户
            try {
                User user = localAuth.getUser();

                int effectNum = userDao.insertUser(user);
                if (effectNum < 0)
                    throw new RuntimeException("添加user失败");
            }catch (Exception e){
                throw new RuntimeException("error: " + e.getMessage());
            }

            long userId = 0l;
            User userCondition = new User();
            userCondition.setName(localAuth.getUserName());
            List<User> users = userDao.queryUserList(userCondition, 0, 5);
            userId = users.get(0).getUserId();


            localAuth.getUser().setUserId(userId);

            // 插入账号
            int effectNum =  localAuthDao.insertLocalAuth(localAuth);
            if (effectNum <= 0)
                throw new RuntimeException("创建失败");
            else
                return new LocalAuthExecution(LocalAuthStateEnum.INNER_FAIL.getState(), LocalAuthStateEnum.INNER_FAIL.getStateInfo());

        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }


    /**
     * 修改密码
     *
     * @param userId
     * @param username
     * @param password
     * @param newPass
     * @return
     */
    @Override
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPass) {

        // 数据校验
        if (userId != null && username != null && password != null && newPass != null && !password.equals(newPass)){
            try {
                int effectNum = localAuthDao.updateLocalAuth(userId, username, MD5Util.getMD5(password), MD5Util.getMD5(newPass), new Date());

                if (effectNum < 0){
                    throw new RuntimeException("修改密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS.getState(), LocalAuthStateEnum.SUCCESS.getStateInfo());
            }catch (Exception e){
                throw new RuntimeException("error: " + e.getMessage());
            }
        }else {
            return new LocalAuthExecution(LocalAuthStateEnum.EMPTY.getState(), LocalAuthStateEnum.EMPTY.getStateInfo());
        }
    }


    /**
     * 绑定账号
     *
     * @param localAuth
     * @return
     */
    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) {
        if (localAuth == null || localAuth.getUserName() == null ||localAuth.getPassword() == null
                || localAuth.getUserId() == null){
            return new LocalAuthExecution(LocalAuthStateEnum.EMPTY.getState(), LocalAuthStateEnum.EMPTY.getStateInfo());
        }

        // 是否已存在账号
        LocalAuth temp = localAuthDao.queryLocalAuthById(localAuth.getUserId());
        if (temp != null){
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT.getState(), LocalAuthStateEnum.ONLY_ONE_ACCOUNT.getStateInfo());
        }

        // 添加账号
        try {
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            localAuth.setPassword(MD5Util.getMD5(localAuth.getPassword()));

            int effectNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectNum <= 0)
                throw new RuntimeException("账号绑定失败");
            else
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS.getState(), LocalAuthStateEnum.SUCCESS.getStateInfo());
        }catch (Exception e){
            throw new RuntimeException("error: " + e.getMessage());
        }
    }

    private void addProfileImg(LocalAuth localAuth,
                               CommonsMultipartFile profileImg) {
        String dest = PathUtil.getImgBasePath();
        String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
        localAuth.getUser().setProfileImg(profileImgAddr);
    }
}
