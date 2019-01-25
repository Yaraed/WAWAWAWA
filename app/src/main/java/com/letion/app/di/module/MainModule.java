package com.letion.app.di.module;

import com.letion.app.MainContract;
import com.letion.app.MainModel;
import com.weyee.poscore.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainContract.MainView view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public MainModule(MainContract.MainView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.MainView provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }
}
