package com.heiku.snacks.service;

import com.heiku.snacks.dto.ShopExecution;
import com.heiku.snacks.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

public interface ShopService {

    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);
}
