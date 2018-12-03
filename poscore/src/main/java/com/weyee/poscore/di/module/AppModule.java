package com.weyee.poscore.di.module;

import android.app.Application;

import com.weyee.poscore.base.integration.IRepositoryManager;
import com.weyee.poscore.base.integration.RepositoryManager;

import java.util.Map;

import javax.inject.Singleton;

import androidx.collection.ArrayMap;
import dagger.Module;
import dagger.Provides;

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
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }

    @Singleton
    @Provides
    public Map<String, Object> provideExtras(){
        return new ArrayMap<>();
    }
}
