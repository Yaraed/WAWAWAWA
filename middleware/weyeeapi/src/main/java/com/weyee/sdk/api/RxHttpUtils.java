package com.weyee.sdk.api;

import android.annotation.SuppressLint;
import com.weyee.sdk.api.base.GlobalRxHttp;
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

    public GlobalRxHttp config() {
        return GlobalRxHttp.getInstance();
    }

    /**
     * 使用全局参数创建请求
     *
     * @param cls Class
     * @param <K> K
     * @return 返回
     */
    public static <K> K createApi(Class<K> cls) {
        return GlobalRxHttp.createGApi(cls);
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
