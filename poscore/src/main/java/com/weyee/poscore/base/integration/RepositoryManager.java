package com.weyee.poscore.base.integration;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.core.util.Preconditions;
import com.weyee.sdk.api.RxCacheUtils;
import com.weyee.sdk.api.RxHttpUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 需要在{@link IConfigModule}的实现类中先inject需要的服务
 * Created by liu-feng on 2017/6/5.
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    //private Retrofit mRetrofit;
    private final Map<String, Object> mRetrofitServiceCache = new LinkedHashMap<>();
    private final Map<String, Object> mCacheServiceCache = new LinkedHashMap<>();

    @Inject
    public RepositoryManager() {
        /**
         * @see RxHttpUtils#createApi(Class)
         * 不自动注入Retrofit，使用全局单例的RxHttpUtils createApi
         */
        //this.mRetrofit = retrofit;
    }

    /**
     * 注入RetrofitService,在{@link IConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     * <p>
     * 现在不自动注入，使用的是本地注入的方式，手动去调用该方法创建Retrofit Service
     * 原因： ①自动注入生命周期不可控
     * ②自动注入所有的Service都被注入，按需注入
     * ③自动注入代码可读性很差
     *
     * @param services
     * @see #obtainRetrofitService(Class)
     */
    @Override
    public void injectRetrofitService(Class<?>... services) {
        for (Class<?> service : services) {
            if (mRetrofitServiceCache.containsKey(service.getName())) continue;
            mRetrofitServiceCache.put(service.getName(), RxHttpUtils.createApi(service));
        }

    }

    /**
     * 注入CacheService,在{@link IConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     * <p>
     * 现在不自动注入，使用的是本地注入的方式，手动去调用该方法创建Cache Service
     * 原因： ①自动注入生命周期不可控
     * ②自动注入所有的Service都被注入，按需注入
     * ③自动注入代码可读性很差
     *
     * @param services
     */
    @Override
    public void injectCacheService(Class<?>... services) {
        for (Class<?> service : services) {
            if (mCacheServiceCache.containsKey(service.getName())) continue;
            mCacheServiceCache.put(service.getName(), RxCacheUtils.createApi(service));
        }
    }

    /**
     * 根据传入的Class获取对应的Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        if (!mRetrofitServiceCache.containsKey(service.getName())) {
            injectRetrofitService(service);
        }
        Preconditions.checkState(mRetrofitServiceCache.containsKey(service.getName()), "Unable to find %s,first call injectRetrofitService(%s) in IConfigModule");
        return (T) mRetrofitServiceCache.get(service.getName());
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public <T> T obtainCacheService(Class<T> cache) {
        if (!mCacheServiceCache.containsKey(cache.getName())) {
            injectCacheService(cache);
        }
        Preconditions.checkState(mCacheServiceCache.containsKey(cache.getName()), "Unable to find %s,first call injectCacheService(%s) in IConfigModule");
        return (T) mCacheServiceCache.get(cache.getName());
    }
}
