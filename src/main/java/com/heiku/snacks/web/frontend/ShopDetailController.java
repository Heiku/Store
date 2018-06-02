package com.heiku.snacks.web.frontend;

import com.heiku.snacks.dto.ProductExecution;
import com.heiku.snacks.entity.Product;
import com.heiku.snacks.entity.ProductCategory;
import com.heiku.snacks.entity.Shop;
import com.heiku.snacks.service.ProductCategoryService;
import com.heiku.snacks.service.ProductService;
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
public class ShopDetailController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 获取商铺信息， 商品类别
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        // 获取店铺信息及商品类别列表
        Shop shop = null;
        List<ProductCategory> productCategoryList = new ArrayList<>();
        if (shopId != -1){
            shop = shopService.getByShopId(shopId);

            productCategoryList = productCategoryService.getProductCategory(shopId);

            modelMap.put("success", true);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "未指定商铺");
        }
        return modelMap;
    }


    @RequestMapping(value = "listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // 获取页数 和 页码
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");

        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        if ((pageIndex > -1) && (pageSize > -1)){

            // 获取商品类别id
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategory");

            // 获取搜索框名字
            String productName = HttpServletRequestUtil.getString(request, "productName");

            // 整合搜索
            Product product = compactSearch(shopId, productCategoryId, productName);

            ProductExecution execution = productService.getProductList(product, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("productList", execution.getProductList());
            modelMap.put("count", execution.getCount());
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }


    private Product compactSearch(long shopId, long productCategoryId, String productName){
        Product product = new Product();

        if (shopId != -1) {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            product.setShop(shop);
        }

        if (productCategoryId != -1){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            product.setProductCategory(productCategory);
        }

        if (productName != null){
            product.setProductName(productName);
        }

        product.setEnableStatus(1);

        return product;
    }
}
