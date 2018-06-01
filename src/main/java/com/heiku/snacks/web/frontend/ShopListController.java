package com.heiku.snacks.web.frontend;


import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Area;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.entity.ShopCategory;
import com.heiku.snacks.service.AreaService;
import com.heiku.snacks.service.ShopCategoryService;
import com.heiku.snacks.service.ShopService;
import com.heiku.snacks.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;


    /**
     * 获取店铺类别列表和区域列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopPageInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        long parentId = HttpServletRequestUtil.getLong(request, "parentId");

        // 获取商店类别列表
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        // 传入parentId，查询子商店类别列表
        if (parentId != -1){
            try {
                ShopCategory shopCategory = new ShopCategory();
                ShopCategory parentShopCtegory = new ShopCategory();
                parentShopCtegory.setShopCategoryId(parentId);
                shopCategory.setParent(parentShopCtegory);

                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

        }else {
            // parentId不存在，返回一级商店类别列表
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }

        modelMap.put("shopCategoryList", shopCategoryList);


        // 获取区域列表
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();

            modelMap.put("success", true);
            modelMap.put("areaList", areaList);

            return modelMap;
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }


    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // 获取页数 和 页码
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");

        if ((pageIndex > -1) && (pageSize > -1)){

            // 获取一级类别 和 二级类别
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");

            // 获取其余信息
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");

            // 获取搜索框名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");

            // 整合查询条件
            Shop shopCondition = compactSearch(areaId, parentId, shopCategoryId, shopName);

            ShopExecution execution = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("shopList", execution.getShopList());
            modelMap.put("count", execution.getCount());

        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }


    private Shop compactSearch(int areaId, long parentId, long shopCategoryId, String shopName){
        Shop shop = new Shop();

        // 区域列表
        if (areaId != -1L){
            Area area = new Area();
            area.setAreaId(areaId);
            shop.setArea(area);
        }

        // 一级列表下的所有二级列表店铺
        if (parentId != -1L){
            ShopCategory shopCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();

            parentCategory.setShopCategoryId(parentId);
            shopCategory.setParent(parentCategory);

            shop.setShopCategory(shopCategory);
        }

        // 指定二级列表下的所有店铺
        if (shopCategoryId != -1){
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);

            shop.setShopCategory(shopCategory);
        }

        // 名字查询
        if (shopName != null){
            shop.setShopName(shopName);
        }

        shop.setEnableStatus(1);

        return shop;
    }
}
