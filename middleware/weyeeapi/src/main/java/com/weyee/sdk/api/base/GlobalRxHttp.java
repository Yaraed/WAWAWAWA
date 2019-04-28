package com.weyee.sdk.api.base;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>网络请求工具类---使用的是全局配置的变量
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/10 0010
 */
public class GlobalRxHttp {
    private static GlobalRxHttp instance;

    /**
     * 适配多套API规则， tag-请求Client对，用于处理多返回结果和多url的情况
     */
    private Map<String, BaseRetrofitClient> retrofitClientMap;

    /**
     * 缓存retrofit针对同一个ApiService不会重复创建retrofit对象
     */
    private Map<String, Object> retrofitServiceCache;

    /**
     * 默认的HTTP URL
     */
    private String defaultBaseUrl;
    private OkHttpClient defaultOkhttpClient;


    private GlobalRxHttp() {
        retrofitClientMap = new HashMap<>();
        retrofitServiceCache = new HashMap<>();
    }

    public static GlobalRxHttp getInstance() {
        if (instance == null) {
            synchronized (GlobalRxHttp.class) {
                if (instance == null) {
                    instance = new GlobalRxHttp();
                }
            }

        }
        return instance;
    }

    public GlobalRxHttp baseUrl(String defaultBaseUrl) {
        this.defaultBaseUrl = defaultBaseUrl;
        return this;
    }

    /**
     * 自定义的OKHTTP
     *
     * @param defaultOkhttpClient
     * @return
     */
    public GlobalRxHttp client(OkHttpClient defaultOkhttpClient) {
        this.defaultOkhttpClient = defaultOkhttpClient;
        return this;
    }

    /**
     * 全局的 retrofit
     *
     * @return
     */
    private BaseRetrofitClient getRetrofit(@Nullable Class cls) {
        if (retrofitClientMap == null) {
            retrofitClientMap = new HashMap<>();
        }
        String tag = null;
        try {
            tag = (String) (cls != null ? cls.getField("baseUrl").get(null) : null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (retrofitClientMap.containsKey(tag)) {
            return retrofitClientMap.get(tag);
        } else {
            retrofitClientMap.put(tag == null || tag.equals(defaultBaseUrl) ? null : tag, new SimpleRetrofitClient().baseUrl(tag == null ? defaultBaseUrl : tag).client(defaultOkhttpClient).build());
            return retrofitClientMap.get(tag == null || tag.equals(defaultBaseUrl) ? null : tag);
        }
    }

    /**
     * 使用全局变量的请求
     *
     * @param cls
     * @param <K>
     * @return
     */
    public <K> K createGApi(final Class<K> cls) {
        if (retrofitServiceCache == null) {
            retrofitServiceCache = new HashMap<>();
        }
        K retrofitService = (K) retrofitServiceCache.get(cls.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = (K) getRetrofit(cls).create(cls);
            retrofitServiceCache.put(cls.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }
}
