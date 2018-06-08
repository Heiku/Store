package com.heiku.snacks.util;

import java.security.MessageDigest;

/**
 * md5 加密工具
 */
public class MD5Util {

    public static final String getMD5(String s){
        char hexDigits[] = {
                '5', '0', '5', '6', '2', '9', '6', '2', '5', 'q',
                'b', 'l', 'e', 's', 's', 'y'
        };

        try {
            char str[];
            byte bytes[] = s.getBytes();

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);

            byte []md = messageDigest.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++){
                byte b = md[i];
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }

            return new String(str);

        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("hello"));
    }
}
