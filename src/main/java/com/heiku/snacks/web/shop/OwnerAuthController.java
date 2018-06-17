package com.heiku.snacks.web.shop;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiku.snacks.dto.LocalAuthExecution;
import com.heiku.snacks.entity.LocalAuth;
import com.heiku.snacks.entity.User;
import com.heiku.snacks.enums.LocalAuthStateEnum;
import com.heiku.snacks.service.LocalAuthService;
import com.heiku.snacks.util.CodeUtil;
import com.heiku.snacks.util.HttpServletRequestUtil;
import com.heiku.snacks.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
public class OwnerAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    @RequestMapping(value = "/ownerlogincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> ownerLoginCheck(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // 验证
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入错误的验证码");

            return modelMap;
        }

        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");

        if (userName != null && password != null){
            password = MD5Util.getMD5(password);

            LocalAuth localAuth = localAuthService.checkLocalAuth(userName, password);
            if (localAuth != null){
                if (localAuth.getUser().getUserType() == 1){
                    modelMap.put("success", true);
                    request.getSession().setAttribute("user", localAuth.getUser());
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "非管理员无法访问");
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码不能为空");
        }
        return modelMap;
    }



    @RequestMapping(value = "/ownerregister", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> ownerRegister(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }

        ObjectMapper mapper = new ObjectMapper();
        LocalAuth localAuth = null;

        // 获取注册信息
        String localAuthStr = HttpServletRequestUtil.getString(request, "localAuthStr");

        MultipartHttpServletRequest multipartHttpServletRequest = null;
        CommonsMultipartFile thumbnail = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());


        // 提取文件
        if (commonsMultipartResolver.isMultipart(request)){
            multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            thumbnail = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }

        // 实体类获取
        try {
            localAuth = mapper.readValue(localAuthStr, LocalAuth.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());

            return modelMap;
        }

        if (localAuth != null &&
                localAuth.getUserName() != null && localAuth.getPassword() != null){
            try {
                User user = (User) request.getSession().getAttribute("user");

                if (user != null && localAuth.getUserId() != null){
                    localAuth.getUser().setUserId(user.getUserId());
                }

                localAuth.getUser().setUserType(1);
                LocalAuthExecution execution = localAuthService.register(localAuth, thumbnail);

                // success
                if (execution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());

                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请补充完整注册信息");
        }

        return modelMap;
    }


    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        User user = (User) request.getSession().getAttribute("user");

        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");

        if (userName != null && password != null && user != null && user.getUserId() != null){

            password = MD5Util.getMD5(password);

            LocalAuth localAuth = new LocalAuth();
            localAuth.setUserName(userName);
            localAuth.setPassword(password);
            localAuth.setUserId(user.getUserId());

            LocalAuthExecution execution = localAuthService.bindLocalAuth(localAuth);

            if (execution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", execution.getStateInfo());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码不能为空");
        }
        return modelMap;
    }


    @RequestMapping(value = "/changepwdcheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changePwdCheck(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");

        User user = (User) request.getSession().getAttribute("user");

        Long userId = 0L;
        if (user != null && user.getUserId() != null){
            userId = user.getUserId();
        }else {
            userId = 1l;
        }

        if (userName != null && password != null && newPassword != null
                    && userId > 0 && !password.equals(newPassword)){
            try {
                LocalAuthExecution execution = localAuthService.modifyLocalAuth(userId, userName,
                        password, newPassword);

                if (execution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "密码为空 或 密码不一致");
            return modelMap;
        }
        return modelMap;
    }



    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> ownerLogout(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("shopList", null);
        request.getSession().setAttribute("currentShop", null);

        modelMap.put("success", true);
        return modelMap;

    }

}
