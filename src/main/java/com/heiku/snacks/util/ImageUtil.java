package com.heiku.snacks.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat  dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();

    public static void generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail);

        // create dir
        makeDirPath(targetAddr);

        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getInputStream())
                    .size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/girl.jpg")), 0.25f)
                    .outputQuality(0.8f)
                    .toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * random filename
     * @return
     */
    public static String getRandomFileName(){
        int randomNum = random.nextInt(89999) + 10000;
        String nowTimeStr = dateformat.format(new Date());
        return nowTimeStr + randomNum;
    }


    /**
     * extensive filename
     * @param file
     * @return
     */
    public static String getFileExtension(CommonsMultipartFile file){
        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }


    private static void makeDirPath(String targetAddr){
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = 
    }



    public static void main(String[] args) throws Exception{


        Thumbnails.of(new File("D:\\data\\pic\\google.jpg"))
                .size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/girl.jpg")), 0.25f)
                .outputQuality(0.8f)
                .toFile("D:\\data\\pic\\newGoogle.jpg");
    }
}
