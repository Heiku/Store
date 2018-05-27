package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.ProductDao;
import com.heiku.snacks.dao.ProductImgDao;
import com.heiku.snacks.dto.ProductExecution;
import com.heiku.snacks.entity.Product;
import com.heiku.snacks.entity.ProductImg;
import com.heiku.snacks.enums.ProductStateEnum;
import com.heiku.snacks.exception.ProductOperationException;
import com.heiku.snacks.service.ProductService;
import com.heiku.snacks.util.ImageUtil;
import com.heiku.snacks.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);

            // 上传图片判断,同时设置商品图片地址属性
            if (thumbnail != null){
                addThumbail(product, thumbnail);
            }

            try {
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0){
                    throw new ProductOperationException("创建失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("失败原因：" + e.getMessage());
            }

            if (productImgs != null && productImgs.size() > 0){
                addProductImgList(product, productImgs);
            }

            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }


    /**
     * 图片的上传，并设置属性
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbail(Product product, CommonsMultipartFile thumbnail){
        String destion = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, destion);
        product.setImgAddr(thumbnailAddr);
    }



    private void addProductImgList(Product product, List<CommonsMultipartFile> productImgList){
        String destion = PathUtil.getShopImagePath(product.getShop().getShopId());

        List<ProductImg> productImgs = new ArrayList<>();
        for (CommonsMultipartFile productImgFile : productImgList){
            String productImgAddr = ImageUtil.generateNormalImg(productImgFile, destion);

            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImg.setImgAddr(productImgAddr);


            productImgs.add(productImg);
        }

        // 当list长度>0，即有图片上传时
        if (productImgs.size() > 0){
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgs);
                if (effectNum <= 0){
                    throw new ProductOperationException("添加图片失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("添加图片失败：" + e.getMessage());
            }
        }
    }
}
