package com.heiku.snacks.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index(){
        return "frontend/index";
    }


    @RequestMapping(value = "/myrecord", method = RequestMethod.GET)
    private String myRecord(){
        return "frontend/myRecord";
    }

    @RequestMapping(value = "/mypoint", method = RequestMethod.GET)
    private String myPoint(){
        return "frontend/myPoint";
    }

    @RequestMapping(value = "/pointRecord", method = RequestMethod.GET)
    private String pointRecord(){
        return "frontend/pointRecord";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String shopList(){
        return "frontend/shopList";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String shopDetail(){
        return "frontend/shopDetail";
    }
}
