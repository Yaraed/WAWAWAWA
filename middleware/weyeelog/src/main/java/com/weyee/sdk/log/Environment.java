package com.weyee.sdk.log;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public class Environment implements IEnvironment {
    public static boolean isDebug() {
        return getEnvironment() != RELEASE;
    }

    public static int getEnvironment() {
        return DEV;
    }
}
