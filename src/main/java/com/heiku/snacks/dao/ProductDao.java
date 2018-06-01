package com.heiku.snacks.dao;

import com.heiku.snacks.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {


    /**
     * 分页查询所有商品
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition")Product productCondition,
                                   @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);


    /**
     * 插入指定的商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);


    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);


    /**
     * 删除指定的商品
     *
     * @param productId
     * @param shopId
     * @return
     */
    int deleteProduct(@Param("productId")long productId, @Param("shopId")long shopId);


    /**
     * 通过id查询商品信息
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);


    /**
     * 查询指定商品属性的总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);
}
