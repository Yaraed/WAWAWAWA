package com.weyee.sdk.api.base;

import android.content.Context;
import android.text.TextUtils;
import com.weyee.sdk.api.cookie.CookieJarImpl;
import com.weyee.sdk.api.cookie.store.CookieStore;
import com.weyee.sdk.api.interceptor.*;
import com.weyee.sdk.log.Environment;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private boolean initCustomClient = false;
    private static final long defaultCacheSize = 1024 * 1024 * 100;
    private static final long defaultTimeout = 10;

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

    /**
     * 初始化默认的okhttp client builder
     *
     * @return
     */
    private OkHttpClient.Builder getBuilder() {
        return builder;
    }

    /**
     * 是否设置默认的http client
     * false 则使用默认的{@link RetrofitClient#getDefaultOkHttpClient()}
     * true  则使用@{@link Builder#build()}
     *
     * @return
     */
    public boolean isInitCustomClient() {
        return initCustomClient;
    }

    public static class Builder {
        public Context context;
        private Map<String, Object> headerMaps;
        private boolean isDebug;
        private boolean isCache;
        private String cachePath;
        private long cacheMaxSize;
        private CookieStore cookieStore;
        private long readTimeout;
        private long writeTimeout;
        private long connectTimeout;
        private InputStream bksFile;
        private String password;
        private InputStream[] certificates;
        private Interceptor[] interceptors;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setHeaders(Map<String, Object> headerMaps) {
            this.headerMaps = headerMaps;
            return this;
        }

        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder setCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        public Builder setCookieType(CookieStore cookieStore) {
            this.cookieStore = cookieStore;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setAddInterceptor(Interceptor... interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder setSslSocketFactory(InputStream... certificates) {
            this.certificates = certificates;
            return this;
        }

        public Builder setSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
            this.bksFile = bksFile;
            this.password = password;
            this.certificates = certificates;
            return this;
        }


        public OkHttpClient build() {
            setCookieConfig();
            setCacheConfig();
            setHeadersConfig();
            setSslConfig();
            addInterceptors();
            setTimeout();
            setHttpLoggingConfig();
            eventListener();
            HttpClient.getInstance().initCustomClient = true;
            return HttpClient.getInstance().getBuilder().build();
        }

        private void eventListener() {
            HttpClient.getInstance().getBuilder().eventListenerFactory(HttpEventListener.FACTORY);
        }

        private void addInterceptors() {
            if (null != interceptors) {
                for (Interceptor interceptor : interceptors) {
                    HttpClient.getInstance().getBuilder().addInterceptor(interceptor);
                }
            }
        }

        /**
         * 配置打印日志
         */
        private void setHttpLoggingConfig() {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new com.weyee.sdk.api.interceptor.HttpLoggingInterceptor());
            loggingInterceptor.setLevel(Environment.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            HttpClient.getInstance().getBuilder().addInterceptor(loggingInterceptor);
        }


        /**
         * 配置headers
         */
        private void setHeadersConfig() {
            if (headerMaps != null) {
                // TODO
            }
            HttpClient.getInstance().getBuilder().addInterceptor(new AddHeaderInterceptor());
        }

        /**
         * 配饰cookie保存到sp文件中
         */
        private void setCookieConfig() {
            if (null != cookieStore) {
                HttpClient.getInstance().getBuilder().cookieJar(new CookieJarImpl(cookieStore));
            } else {
                HttpClient.getInstance().getBuilder().addInterceptor(new AddCookiesInterceptor());  // 设置本地cookie
                HttpClient.getInstance().getBuilder().addInterceptor(new RecvCookiesInterceptor()); // 接受服务端返回的cookie
            }
        }

        /**
         * 配置缓存
         */
        private void setCacheConfig() {
            File externalCacheDir = context.getExternalCacheDir();
            if (null == externalCacheDir) {
                return;
            }
            String defaultCachePath = externalCacheDir.getPath() + "/retrofit";
            if (isCache) {
                Cache cache;
                if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
                    cache = new Cache(new File(cachePath), cacheMaxSize);
                } else {
                    cache = new Cache(new File(defaultCachePath), defaultCacheSize);
                }
                HttpClient.getInstance().getBuilder()
                        .cache(cache)
                        .addInterceptor(new LocalCacheInterceptor())
                        .addNetworkInterceptor(new HttpCacheInterceptor());
            }
        }

        /**
         * 配置超时信息
         */
        private void setTimeout() {
            HttpClient.getInstance().getBuilder().readTimeout(readTimeout == 0 ? defaultTimeout : readTimeout, TimeUnit.SECONDS);
            HttpClient.getInstance().getBuilder().writeTimeout(writeTimeout == 0 ? defaultTimeout : writeTimeout, TimeUnit.SECONDS);
            HttpClient.getInstance().getBuilder().connectTimeout(connectTimeout == 0 ? defaultTimeout : connectTimeout, TimeUnit.SECONDS);
            HttpClient.getInstance().getBuilder().retryOnConnectionFailure(true);
        }

        /**
         * 配置证书
         */
        private void setSslConfig() {
            SSLUtils.SSLParams sslParams;
            if (null == certificates) {
                //信任所有证书,不安全有风险
                sslParams = SSLUtils.getSslSocketFactory();
            } else {
                if (null != bksFile && !TextUtils.isEmpty(password)) {
                    //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
                } else {
                    //使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(certificates);
                }
            }
            HttpClient.getInstance().getBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        }
    }
}
