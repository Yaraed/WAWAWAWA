package com.weyee.poscore.base.integration;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.util.Preconditions;

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
        //this.mRetrofit = retrofit;
    }

    /**
     * 注入RetrofitService,在{@link IConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     *
     * @param services
     */
    @Override
    public void injectRetrofitService(Class<?>... services) {
//        for (Class<?> service : services) {
//            if (mRetrofitServiceCache.containsKey(service.getName())) continue;
//            mRetrofitServiceCache.put(service.getName(), mRetrofit.create(service));
//        }

    }

    /**
     * 注入CacheService,在{@link IConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     *
     * @param services
     */
    @Override
    public void injectCacheService(Class<?>... services) {

    }

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
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
        Preconditions.checkState(mCacheServiceCache.containsKey(cache.getName()), "Unable to find %s,first call injectCacheService(%s) in IConfigModule");
        return (T) mCacheServiceCache.get(cache.getName());
    }
}
