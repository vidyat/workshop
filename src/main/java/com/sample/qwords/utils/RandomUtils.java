package com.sample.qwords.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;

import javax.crypto.NoSuchPaddingException;

/**
 * This class provides utility methods for random operations.
 * 
 * Note: This code was copied from the internet and needs to be refactored.
 * The plan is to add AWS functionality to this application in the future.
 */
public class RandomUtils {
    static Cipher cipher;

    static final String CIPHER = "DES";

    public static AWSCredentials getCredentials(String accessKey, String secretKey) {
        AWSCredentials creds = getCreds(accessKey, secretKey);
        return creds;
    }

    static AWSCredentials getCreds(String id, String key) {
        return new BasicAWSCredentials(id, key);
    }

    public static Cipher getCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // or handle the exception in an appropriate way
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cipher;
    }

}