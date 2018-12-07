package com.weyee.sdk.api;

import android.annotation.SuppressLint;
import com.weyee.sdk.api.dispose.DisposeManager;

/**
 * <p>网络请求
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class RxHttpUtils {
    @SuppressLint("StaticFieldLeak")
    private static RxHttpUtils instance;

    public static RxHttpUtils getInstance() {
        if (instance == null) {
            synchronized (RxHttpUtils.class) {
                if (instance == null) {
                    instance = new RxHttpUtils();
                }
            }

        }
        return instance;
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll() {
        DisposeManager.get().cancelAll();
    }

    /**
     * 取消某个或某些请求
     */
    public static void cancel(Object... tag) {
        DisposeManager.get().cancel(tag);
    }
}
