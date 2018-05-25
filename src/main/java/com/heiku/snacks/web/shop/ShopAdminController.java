package com.heiku.snacks.web.shop;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = RequestMethod.GET)
public class ShopAdminController {


    @RequestMapping(value = "/shopOperation")
    public String shopOperation(){
        return "shop/shopOperation";
    }


    @RequestMapping(value = "/shopList")
    public String shopList(){
        return "shop/shopList";
    }


    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        return "shop/shopManagement";
    }


    @RequestMapping(value = "/productcategorymanage", method = RequestMethod.GET)
    private String productCategoryManage(){
        return "shop/productCategoryManage";
    }
}
