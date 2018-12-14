package com.letion.app.di.component;


import com.letion.app.MainActivity;
import com.letion.app.di.module.MainModule;
import com.weyee.poscore.di.component.AppComponent;
import com.weyee.poscore.di.scope.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(modules = MainModule.class,dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
