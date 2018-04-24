package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.exception.DaoException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to work with files
 *
 * @author Yusupov Danil
 */
class DaoMaintenance {

    /**
     * Collects bytes from {@code InputStream} into bytes array
     * @param inputStream target source
     * @return array of bytes
     */
    static byte[] toByteArray(InputStream inputStream){
        try(InputStream in = inputStream;
            ByteArrayOutputStream out = new ByteArrayOutputStream()){
            int a;
            while ((a = in.read()) != -1){
                out.write(a);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new DaoException("Error reading avatar from DB: ", e);
        }
    }

    static String getTableName(String propertyName){
        Properties properties = new Properties();
        try {
            properties.load(DaoMaintenance.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new DaoException("Error reading properties from '/db.properties' file: ", e);
        }
        return properties.getProperty(propertyName);
    }

}
