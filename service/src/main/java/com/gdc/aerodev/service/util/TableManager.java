package com.gdc.aerodev.service.util;

import com.gdc.aerodev.service.exception.ServiceException;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Properties;

public class TableManager {

    @Bean
    public static String getTableName(String propertyName){
        Properties properties = new Properties();
        try {
            properties.load(TableManager.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new ServiceException("Error reading properties from '/db.properties' file: ", e);
        }
        return properties.getProperty(propertyName);
    }

}
