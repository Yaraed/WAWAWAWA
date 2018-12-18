package com.weyee.sdk.api.core.util;

/**
 * <p>随机字符串工具类。</p>
 *
 * @author moguangjian
 * @date 2016/12/8 20:49
 */
public class RandomHelper {

    /**
     * 获取随机字符串
     *
     * @param length 长度
     * @return
     */
    public static String getRandomString(int length) {
        String string = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }
}
