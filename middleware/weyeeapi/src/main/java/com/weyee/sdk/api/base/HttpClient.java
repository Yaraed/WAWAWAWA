package com.weyee.sdk.api.base;

import okhttp3.OkHttpClient;

/**
 * <p>okHttp client
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class HttpClient {

    private static HttpClient instance;
    private OkHttpClient.Builder builder;

    private HttpClient() {
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance() {

        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }

        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }
}
