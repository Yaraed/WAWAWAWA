package com.weyee.poscore.di.module;

import android.app.Application;
import androidx.collection.ArrayMap;
import com.weyee.poscore.base.integration.IRepositoryManager;
import com.weyee.poscore.base.integration.RepositoryManager;
import com.weyee.sdk.imageloader.BaseImageLoaderStrategy;
import com.weyee.sdk.imageloader.glide.GlideImageLoaderStrategy;
import com.weyee.sdk.router.Nav;
import com.weyee.sdk.router.NavImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by liu-feng on 2017/6/5.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    public BaseImageLoaderStrategy provideImageLoaderStrategy() {
        return new GlideImageLoaderStrategy();
    }

    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }

    @Singleton
    @Provides
    public Nav provideNavigation(NavImpl nav) {
        return nav;
    }

    @Singleton
    @Provides
    public Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }
}
