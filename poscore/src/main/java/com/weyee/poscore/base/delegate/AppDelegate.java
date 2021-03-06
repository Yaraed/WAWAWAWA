package com.weyee.poscore.base.delegate;

import android.app.Application;
import android.content.Context;
import com.weyee.poscore.base.App;
import com.weyee.poscore.base.integration.ActivityLifecycle;
import com.weyee.poscore.base.integration.IConfigModule;
import com.weyee.poscore.base.integration.ManifestParser;
import com.weyee.poscore.di.component.AppComponent;
import com.weyee.poscore.di.component.DaggerAppComponent;
import com.weyee.poscore.di.module.AppModule;
import com.weyee.poscore.di.module.CustomModule;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * AppDelegate可以代理Application的生命周期,在对应的生命周期,执行对应的逻辑,因为Java只能单继承
 * 而我的框架要求Application要继承于BaseApplication
 * 所以当遇到某些三方库需要继承于它的Application的时候,就只有自定义Application继承于三方库的Application
 * 再将BaseApplication的代码复制进去,而现在就不用再复制代码,只用在对应的生命周期调用AppDelegate对应的方法(Application一定要实现APP接口)
 * <p>
 * Created by liu-feng on 2017/6/5.
 */
public class AppDelegate implements App {
    private Application mApplication;
    private AppComponent mAppComponent;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    private final List<IConfigModule> mModules;
    private List<Lifecycle> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    public AppDelegate(Context context) {
        this.mModules = new ManifestParser(context).parse();
        for (IConfigModule module : mModules) {
            module.injectAppLifecycle(context, mAppLifecycles);
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    public void attachBaseContext(Application application) {
        for (Lifecycle lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(application);
        }
    }

    public void onCreate(Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent.builder()
                //提供application
                .appModule(new AppModule(mApplication))
                //全局配置
                .customModule(customModule(mApplication, mModules))
                .build();
        mAppComponent.inject(this);

        mAppComponent.extras().put(IConfigModule.class.getName(), mModules);

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        for (IConfigModule module : mModules) {
            module.registerComponents(mApplication, mAppComponent.repositoryManager());
        }

        for (Lifecycle lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }

    }


    public void onTerminate() {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (Lifecycle lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }


    public void onLowMemory() {
        // TODO 释放当前应用的非必须的内存资源
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (Lifecycle lifecycle : mAppLifecycles) {
                lifecycle.onLowMemory(mApplication);
            }
        }
    }

    public void onTrimMemory(int level) {
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (Lifecycle lifecycle : mAppLifecycles) {
                lifecycle.onTrimMemory(level,mApplication);
            }
        }
    }


    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link IConfigModule}的实现类,和Glide的配置方式相似
     *
     * @return
     */
    private CustomModule customModule(Application context, List<IConfigModule> modules) {

        CustomModule.Builder builder = CustomModule.builder();

        for (IConfigModule module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    public interface Lifecycle {
        void attachBaseContext(Application application);

        void onCreate(Application application);

        void onTerminate(Application application);

        void onLowMemory(Application application);

        void onTrimMemory(int level, Application application);
    }

}

