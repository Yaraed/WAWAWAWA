package com.weyee.poscore.di.component;

import android.app.Application;
import com.weyee.poscore.base.delegate.AppDelegate;
import com.weyee.poscore.base.integration.IRepositoryManager;
import com.weyee.poscore.di.module.AppModule;
import com.weyee.poscore.di.module.CustomModule;
import com.weyee.sdk.imageloader.ImageLoader;
import com.weyee.sdk.router.Nav;
import dagger.Component;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by liu-feng on 2017/6/5.
 */
@Singleton
@Component(modules = {AppModule.class, CustomModule.class})
public interface AppComponent {
    Application Application();

    // 获取图片加载管理器
    ImageLoader imageLoader();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    // 用于路由跳转，统一跳转方式及缓存减少对象的创建
    Nav navigation();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    void inject(AppDelegate delegate);
}
