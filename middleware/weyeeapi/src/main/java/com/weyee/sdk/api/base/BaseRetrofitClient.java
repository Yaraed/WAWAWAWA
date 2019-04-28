package com.weyee.sdk.api.base;

import com.weyee.sdk.api.interceptor.*;
import com.weyee.sdk.log.Environment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * Retrofit的基类，用于支持多套API规则
 *
 * @author wuqi by 2019/4/28.
 */
public interface BaseRetrofitClient<T> {
    T baseUrl(String baseUrl);

    T client(OkHttpClient okClient);

    T build();

    <K> K create(final Class<K> service);

    /**
     * 当没有配置默认的okhttp client，使用默认的
     *
     * @return
     */
    default OkHttpClient defaultOkHttpClient() {
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
}
