package com.weyee.sdk.api;

import android.annotation.SuppressLint;
import com.weyee.sdk.api.rxcache.GsonSpeaker;
import com.weyee.sdk.util.Tools;
import io.rx_cache2.internal.RxCache;

import java.io.File;

/**
 * RxCache 支持http缓存，类似Retrofit语法
 *
 * @author wuqi by 2019/5/28.
 */
public class RxCacheUtils {
    @SuppressLint("StaticFieldLeak")
    private static RxCacheUtils instance;
    private RxCache rxCache;

    public static RxCacheUtils getInstance() {
        if (instance == null) {
            synchronized (RxCacheUtils.class) {
                if (instance == null) {
                    instance = new RxCacheUtils();
                }
            }

        }
        return instance;
    }

    private RxCacheUtils() {
        RxCache.Builder builder = new RxCache.Builder();
        builder.useExpiredDataIfLoaderNotAvailable(true);
        File file = new File(Tools.getApp().getCacheDir(), "RxCache");
        if (Tools.createOrExistsDir(file)) {
            this.rxCache = builder.persistence(file, new GsonSpeaker());
        }
    }

    private RxCache getRxCache() {
        return rxCache;
    }

    /**
     * 使用全局参数创建缓存接口
     *
     * @param cls Class
     * @param <K> K
     * @return 返回
     */
    public static <K> K createApi(Class<K> cls) {
        return getInstance().getRxCache().using(cls);
    }
}
