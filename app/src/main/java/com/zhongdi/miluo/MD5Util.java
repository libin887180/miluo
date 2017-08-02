package com.zhongdi.miluo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/7/26.
 */

public class MD5Util {


        private MD5Util() {
        }

        private static final char hexDigits[] =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        public static String toHexString(byte[] bytes) {
            if (bytes == null) return "";
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                hex.append(hexDigits[(b >> 4) & 0x0F]);
                hex.append(hexDigits[b & 0x0F]);
            }
            return hex.toString();
        }


        public static String md5(String string) {
            byte[] encodeBytes = null;
            try {
                encodeBytes = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException neverHappened) {
                throw new RuntimeException(neverHappened);
            } catch (UnsupportedEncodingException neverHappened) {
                throw new RuntimeException(neverHappened);
            }

            return toHexString(encodeBytes);
        }

}
