package com.heiku.snacks.web.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.entity.ShopCategory;
import com.heiku.snacks.entity.User;
import com.heiku.snacks.enums.ShopStateEnum;
import com.heiku.snacks.exception.ShopOperationException;
import com.heiku.snacks.service.AreaService;
import com.heiku.snacks.service.ShopCategoryService;
import com.heiku.snacks.service.ShopService;
import com.heiku.snacks.util.CodeUtil;
import com.heiku.snacks.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1){
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();

                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }

    /**
     * 初始化获取所有商铺列表 和 区域列表
     * @return
     */
    @RequestMapping(value = "getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo(){
        Map<String, Object> modelMap = new HashMap<>();

        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();

        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();

            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }


        return modelMap;
    }




    /**
     * 注册店铺
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        /*// 验证码校验
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }*/


        /**
         * 提取请求实体，文件流
         */
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = mapper.readValue(shopStr, Shop.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());

            return modelMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );

        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }


        /**
         * 注册店铺
         **/

        if (shop != null && shopImg != null){
            User user = (User)request.getSession().getAttribute("user");
            shop.setManager(user);
            ShopExecution execution;
            try {
                execution = shopService.addShop(shop, shopImg);

                if (execution.getState() == ShopStateEnum.CHECK.getState()){
                    modelMap.put("success", true);


                    // 将店铺列表放入 session 中
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<>();

                    }
                    shopList.add(execution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入完整店铺信息");
            return modelMap;
        }
    }

    /**
     * 修改店铺
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request ){
        Map<String, Object> modelMap = new HashMap<>();

        /*// 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }*/


        //提取请求实体，文件流

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = mapper.readValue(shopStr, Shop.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());

            return modelMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );

        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        //修改店铺

        if (shop != null && shop.getShopId() != null){

            ShopExecution execution;
            try {

                if (shopImg == null){
                    execution= shopService.modifyShop(shop, null);
                }else {
                    execution = shopService.modifyShop(shop, shopImg);
                }

                if (execution.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;

        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商铺Id");
            return modelMap;
        }
    }


    /**
     *  获取指定店主下的所有店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        User user = new User();
        user.setUserId(1L);
        user.setName("heiku");
        //user = (User)request.getSession().getAttribute("user");


        try {
            Shop shopCondition = new Shop();
            shopCondition.setManager(user);

            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);

            modelMap.put("shoplist", se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }


    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // 链接判断参数
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        System.out.println(shopId);

        if (shopId <= 0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");

            // 无法从链接和session中找到指定shop，重定向到shoplist选择
            if (currentShopObj == null){
                modelMap.put("redirect", true);
                modelMap.put("url", "/store/shopadmin/shopList");
            }else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }

        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);

            modelMap.put("redirect", false);
        }

        return modelMap;
    }
}
