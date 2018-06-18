package com.gdc.aerodev.service.security;

import com.gdc.aerodev.service.exception.ServiceException;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * My simple password encryptor =)
 */
public class Hasher {

    /**
     * Encrypts password
     * @param target clear password
     * @return {@code String} with encrypted result
     */
    public static String hash(String target){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(target.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error encrypting password: ", e);
        }
    }

}
