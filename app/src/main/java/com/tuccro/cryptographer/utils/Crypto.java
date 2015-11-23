package com.tuccro.cryptographer.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by tuccro on 11/23/15.
 */
public class Crypto {

    /**
     * AES key generator
     *
     * @param password for key
     * @return key in bytes
     * @throws Exception the exception
     */
    public static byte[] generateKey(String password) throws Exception {
        byte[] keyStart = password.getBytes("UTF-8");

        KeyGenerator kGen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(keyStart);
        kGen.init(256, sr);
        SecretKey sKey = kGen.generateKey();
        return sKey.getEncoded();
    }

    /**
     * Encoding bytes by AES
     *
     * @param key      key in bytes
     * @param fileData data for encoding
     * @return encoded data
     * @throws Exception the exception
     */
    public static byte[] encodeFile(byte[] key, byte[] fileData) throws Exception {

        SecretKeySpec sKeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);

        byte[] encrypted = cipher.doFinal(fileData);

        return encrypted;
    }

    /**
     * Decoding bytes by AES
     *
     * @param key      key in bytes
     * @param fileData data in bytes
     * @return result data
     * @throws Exception the exception
     */
    public static byte[] decodeFile(byte[] key, byte[] fileData) throws Exception {
        SecretKeySpec sKeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);

        byte[] decrypted = cipher.doFinal(fileData);

        return decrypted;
    }
}

