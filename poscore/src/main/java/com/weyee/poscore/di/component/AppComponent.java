package com.weyee.poscore.di.component;

import android.app.Application;

import com.weyee.poscore.base.delegate.AppDelegate;
import com.weyee.poscore.base.integration.IRepositoryManager;
import com.weyee.poscore.di.module.AppModule;
import com.weyee.poscore.di.module.OtherModule;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by liu-feng on 2017/6/5.
 */
@Singleton
@Component(modules = {AppModule.class, OtherModule.class})
public interface AppComponent {
    Application Application();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    void inject(AppDelegate delegate);
}
