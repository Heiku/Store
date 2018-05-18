package com.heiku.snacks.dao;

import com.heiku.snacks.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {

    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategory);
}
