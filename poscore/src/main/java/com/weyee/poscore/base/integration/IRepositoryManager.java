package com.weyee.poscore.base.integration;

import android.content.Context;

/**
 * Created by liu-feng on 2017/6/5.
 */
public interface IRepositoryManager {
    /**
     * 注入RetrofitService,在{@link IConfigModule#registerComponents(Context, IRepositoryManager)} 中进行注入
     * @param services
     */
    void injectRetrofitService(Class<?>... services);


    /**
     * 注入CacheService,在{@link IConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     * @param services
     */
    void injectCacheService(Class<?>... services);


    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cache);
}
