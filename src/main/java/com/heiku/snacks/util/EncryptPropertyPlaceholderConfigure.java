package com.heiku.snacks.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigure extends PropertyPlaceholderConfigurer {

    private String[] encryptProNames = {
            "jdbc.username",
            "jdbc.password"
    };

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProperty(propertyName)){
            String dectyptValue = DESUtil.getDecryptString(propertyValue);
            return dectyptValue;
        }else {
            return propertyValue;
        }
    }

    private boolean isEncryptProperty(String propertyName){
        for (String encryptPropertyName : encryptProNames){
            if (encryptPropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
