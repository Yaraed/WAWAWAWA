package com.weyee.sdk.api.base;

import com.weyee.sdk.api.gson.GsonAdapter;
import com.weyee.sdk.api.interceptor.*;
import com.weyee.sdk.log.Environment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * <p>RetrofitClient工具类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit.Builder mRetrofitBuilder;

    private RetrofitClient() {
        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()));
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    /**
     * 当没有配置默认的okhttp client，使用默认的
     *
     * @return
     */
    private OkHttpClient getDefaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new com.weyee.sdk.api.interceptor.HttpLoggingInterceptor());
        loggingInterceptor.setLevel(Environment.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(loggingInterceptor);  // 设置打印日志
        builder.addInterceptor(new AddCookiesInterceptor());  // 设置本地cookie
        builder.addInterceptor(new RecvCookiesInterceptor()); // 接受服务端返回的cookie
        builder.addInterceptor(new AddHeaderInterceptor()); // 添加header
        builder.addInterceptor(new HttpCacheInterceptor()); // 网络缓存
        builder.addInterceptor(new LocalCacheInterceptor()); // 本地缓存
        builder.eventListenerFactory(HttpEventListener.FACTORY); // 监听网络请求的时间
        return builder.build();
    }

    public Retrofit getRetrofit() {
        if (!HttpClient.getInstance().isInitCustomClient()) {
            return mRetrofitBuilder.client(getDefaultOkHttpClient()).build();
        }
        return mRetrofitBuilder.build();
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }
}
