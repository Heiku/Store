package com.heiku.snacks.web.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiku.snacks.dto.ProductExecution;
import com.heiku.snacks.entity.Product;
import com.heiku.snacks.entity.ProductCategory;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.enums.ProductStateEnum;
import com.heiku.snacks.service.ProductCategoryService;
import com.heiku.snacks.service.ProductService;
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
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    // 最大上传图片数量
    private static final int MAXIMAGECOUNT = 6;


    /**
     * 上传包括（product信息， 缩略图， 详情图）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();


        MultipartHttpServletRequest request2 = null;
        if (request instanceof MultipartHttpServletRequest){
            request2 = (MultipartHttpServletRequest)request;
        }
        System.out.println(request2.getParameter("verifyCodeActual"));


        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }

        String productStr = HttpServletRequestUtil.getString(request, "productStr");

        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        MultipartHttpServletRequest multipartRequest = null;
        List<CommonsMultipartFile> productImgList = new ArrayList<>();

        CommonsMultipartFile thumbnail = null;
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        // 分别获取缩略图 和 详情图
        try {
            // 请求中是否存在相关文件
            if (multipartResolver.isMultipart(request)){
                multipartRequest = (MultipartHttpServletRequest)request;

                // 获取缩略图
                thumbnail = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");

                // 获取多个详情图片
                for (int i = 0; i < MAXIMAGECOUNT; i++){
                    CommonsMultipartFile productImgItem =
                            (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);

                    if (productImgItem != null){
                        productImgList.add(productImgItem);
                    }
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片为空！");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传失败！：" + e.getMessage());
            return modelMap;
        }


        // 获取商品具体信息
        try {
            product = mapper.readValue(productStr, Product.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传失败！：" + e.getMessage());
            return modelMap;
        }


        // 数据库 文件复制 操作
        if (product != null && thumbnail != null && productImgList.size() > 0){

            try {
                // 设置shopId
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution execution = productService.addProduct(product, thumbnail, productImgList);

                if (execution.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success", false);
                modelMap.put("errMsg",  "上传失败！：" + e.getMessage());

                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品信息不完整");
        }

        return modelMap;
    }


    /**
     * 获取指定id的商品信息
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId){
        Map<String, Object> modelMap = new HashMap<>();

        if (productId > -1){
            Product product = productService.getProductById(productId);

            List<ProductCategory> productCategoryList =
                    productCategoryService.getProductCategory(product.getShop().getShopId());

            modelMap.put("success", true);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入productId");
        }
        return modelMap;
    }



    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProdcut(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");

        // 判断是否是验证码修改
        if (!statusChange && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }

        // 获取实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;

        // 缩略图
        CommonsMultipartFile thumbnail = null;

        // 获取详情图
        List<CommonsMultipartFile> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        try {
            if (multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

                // 提取缩略图
                thumbnail = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");

                // 提取详情图
                for (int i = 0; i < MAXIMAGECOUNT; i++){
                    CommonsMultipartFile productImgItem =
                            (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);

                    if (productImgItem != null){
                        productImgList.add(productImgItem);
                    }
                }
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        // 获取product对象
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        try {
            product = mapper.readValue(productStr, Product.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        if (product != null){
            try {
                // 获取商铺
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);

                // 执行修改商品
                ProductExecution execution = productService.modifyProduct(product, thumbnail, productImgList);

                if (execution.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", execution.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

}
