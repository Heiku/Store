package com.heiku.snacks.web.shop;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shop")
public class LocalController {


    @RequestMapping(value = "/ownerbind", method = RequestMethod.GET)
    public String localBind(){
        return "shop/ownerBind";
    }


    @RequestMapping(value = "/ownerlogin", method = RequestMethod.GET)
    public String ownerLogin(){
        return "shop/ownerLogin";
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "shop/register";
    }

    @RequestMapping(value = "/changepwd", method = RequestMethod.GET)
    public String changePwd(){
        return "shop/changePsw";
    }

    @RequestMapping(value = "/forgetpwd", method = RequestMethod.GET)
    public String fotgetPwd(){
        return "shop/forgetPsw";
    }




    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String shopList() {
        return "shop/shopList";
    }

}
