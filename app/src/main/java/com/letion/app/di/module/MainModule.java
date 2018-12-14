package com.letion.app.di.module;

import com.letion.app.MainView;
import com.weyee.poscore.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainView view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public MainModule(MainView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainView provideMainView() {
        return this.view;
    }
}
