package com.weyee.sdk.api.core;

import com.weyee.sdk.api.core.exception.ApiConfigException;
import com.weyee.sdk.api.core.exception.InitApiException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <p>okHttp 与 retrofit 实现类。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class HttpRetrofit {

    private static final String TAG = HttpRetrofit.class.getSimpleName();

    private static OkHttpClient httpClient = null;
    private static Retrofit retrofit = null;

    public static synchronized void init(ApiConfig apiConfig) {

        if (apiConfig == null) {
            throw new ApiConfigException();
        }

        initHttp(apiConfig);
        initRetrofit(apiConfig);
    }

    private static void initRetrofit(ApiConfig apiConfig) {
        if (retrofit != null) {
            retrofit = null;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(apiConfig.getHost())
//                .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(ResponseConverterFactory.create())
                .addConverterFactory(apiConfig.getCovertFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpInstance())
                .build();
    }

    private static void initHttp(ApiConfig apiConfig) {
        if (httpClient != null) {
            httpClient = null;
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(chain -> {
            Request.Builder builder1 = chain.request().newBuilder();
            return chain.proceed(builder1.build());
        })
                .addNetworkInterceptor(loggingInterceptor)
                .connectTimeout(apiConfig.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(apiConfig.getTimeout(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        for (Interceptor interceptor : apiConfig.getInterceptors()) {
            builder.addInterceptor(interceptor);
        }

        httpClient = builder.build();
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            throw new InitApiException();
        }

        return retrofit;
    }

    public static OkHttpClient getHttpInstance() {
        if (httpClient == null) {
            throw new InitApiException();
        }

        return httpClient;
    }


}

