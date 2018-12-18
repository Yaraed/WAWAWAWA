package com.weyee.sdk.api.core;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * <p>Api 工厂类。</p>
 *
 * 参考<a>https://www.jianshu.com/p/2e8b400909b7</a>
 * 参考<a>https://www.jianshu.com/p/9674f6df910d</a>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class ApiFactory {

    public static Context context;

    private ApiFactory() {
    }

    public static <T> T create(Class<T> clazz) {
        return HttpRetrofit.getInstance().create(clazz);
    }

    /**
     *
     * @param context  ApplicationContext。请勿使用Activity 的 context 。
     * @param apiConfig
     */
    public static void init(Context context, ApiConfig apiConfig) {
        ApiFactory.context = null;
        ApiFactory.context = context;
        HttpRetrofit.init(apiConfig);
    }

    public static Retrofit getInstance() {
        return HttpRetrofit.getInstance();
    }

    public static OkHttpClient getHttpInstance() {
        return HttpRetrofit.getHttpInstance();
    }

}
