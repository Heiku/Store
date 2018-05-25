package com.heiku.snacks.web.shop;

import com.heiku.snacks.dto.ProductCategoryExecution;
import com.heiku.snacks.dto.Result;
import com.heiku.snacks.entity.ProductCategory;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.enums.ProductCategoryStateEnum;
import com.heiku.snacks.exception.ProductCategoryException;
import com.heiku.snacks.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/shopadmin")
@Controller
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;


    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){


        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        System.out.println(currentShop.getShopId());
        List<ProductCategory> productCategoryList = null;
        if (currentShop != null && currentShop.getShopId() > 0){
            productCategoryList = productCategoryService.getProductCategory(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, productCategoryList);
        }else {
            ProductCategoryStateEnum stateEnum = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, stateEnum.getState(), stateEnum.getStateInfo());
        }

    }



    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                    HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        for (ProductCategory category : productCategoryList){
            category.setShopId(currentShop.getShopId());
        }

        if (productCategoryList != null && productCategoryList.size() > 0){
            try {
                ProductCategoryExecution execution = productCategoryService.batchAddProductCategory(productCategoryList);
                if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            }catch (ProductCategoryException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入的商品类别为空");
        }

        return modelMap;
    }


    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();

        // 判断传入参数
        if (productCategoryId != null && productCategoryId > 0){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution execution = productCategoryService.deleteProductCategory(productCategoryId,
                        currentShop.getShopId());


                // 状态判断
                if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请指定删除的商品类别");
        }

        return modelMap;
    }
}
