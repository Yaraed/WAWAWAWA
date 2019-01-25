package com.weyee.sdk.api.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author moguangjian
 * @date 2016/11/18 18:50
 */
public class ShaHelper {

    /**
     * byte字节转换成16进制的字符串MD5Utils.hexString
     *
     * @param info
     * @param shaType
     */
    public static byte[] eccrypt(String info, String shaType) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance(shaType);
            byte[] srcBytes = info.getBytes();
            // 使用srcBytes更新摘要
            sha.update(srcBytes);
            // 完成哈希计算，得到result
            byte[] resultBytes = sha.digest();
            return resultBytes;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public static byte[] eccryptSHA1(String info) {
        return eccrypt(info, "SHA1");
    }

    public static byte[] eccryptSHA256(String info) {
        return eccrypt(info, "SHA-256");
    }

    public static byte[] eccryptSHA384(String info) {
        return eccrypt(info, "SHA-384");
    }

    public static byte[] eccryptSHA512(String info) {
        return eccrypt(info, "SHA-512");
    }

    public static String encodeSha1(String str) {
        return hexString(eccryptSHA1(str));
    }

    public static String hexString(byte[] bytes) {
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
