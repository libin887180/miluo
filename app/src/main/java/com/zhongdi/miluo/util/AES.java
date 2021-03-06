package com.zhongdi.miluo.util;

/**
 * Created by kenn on 2017/9/20.
 */

import com.zhongdi.miluo.util.covert.BASE64;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This program generates a AES key, retrieves its raw bytes, and then
 * reinstantiates a AES key from the key bytes. The reinstantiated key is used
 * to initialize a AES cipher for encryption and decryption.
 */

public class AES {

    private static final String AES = "AES";

    private static final String CRYPT_KEY = "miluo9646miluo96";

    /**
     * 加密
     *
     * @param
     * @return
     */
    public static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
//        SecretKeySpec securekey = toKey(key);
        cipher.init(Cipher.ENCRYPT_MODE, securekey);//设置密钥和加密形式
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String key)  throws Exception  {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);//设置加密Key
//        SecretKeySpec securekey = toKey(key);
        cipher.init(Cipher.DECRYPT_MODE, securekey);//设置密钥和解密形式
        return cipher.doFinal(src);
    }
    /**
     * 生成一个固定密钥
     *
     * @param password
     *            长度必须是16的倍数
     * @return
     * @throws UnsupportedEncodingException
     */
    private static SecretKeySpec toKey(String password)
            throws UnsupportedEncodingException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();

            System.out.println(new String(enCodeFormat).getBytes());
            return new SecretKeySpec(enCodeFormat, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    /**
//     * 二行制转十六进制字符串
//     *
//     * @param b
//     * @return
//     */
//    public static String byte2hex(byte[] b) {
//        String hs = "";
//        String stmp = "";
//        for (int n = 0; n < b.length; n++) {
//            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
//            if (stmp.length() == 1)
//                hs = hs + "0" + stmp;
//            else
//                hs = hs + stmp;
//        }
//        return hs.toUpperCase();
//    }
//
//    public static byte[] hex2byte(byte[] b) {
//        if ((b.length % 2) != 0)
//            throw new IllegalArgumentException("长度不是偶数");
//        byte[] b2 = new byte[b.length / 2];
//        for (int n = 0; n < b.length; n += 2) {
//            String item = new String(b, n, 2);
//            b2[n / 2] = (byte) Integer.parseInt(item, 16);
//        }
//        return b2;
//    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data) {
        try {

            byte[] decode = BASE64.decode(data);
            byte[] result = decrypt(decode,CRYPT_KEY);
//            return new String(decrypt(hex2byte(data.getBytes()),
//                    CRYPT_KEY));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data) {
        try {
            byte[] result = encrypt(data.getBytes(),
                    CRYPT_KEY);
            return BASE64.encodeToString(result);
//            return byte2hex(encrypt(data.getBytes(), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}