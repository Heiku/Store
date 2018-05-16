package com.heiku.snacks.util;

public class PathUtil {

    private static String seperator = System.getProperty("file.separator");


    /**
     * 根据不同操作系统替换路径分隔符
     * @return
     */
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";

        if (os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/image/";
        }else {
            basePath = "/home/heiku/image/";
        }

        basePath = basePath.replace("/", seperator);
        return basePath;
    }


    /**
     * 根据不同店铺，存储不同文件夹
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
