package com.letion.app;

import android.content.Context;
import com.weyee.poscore.base.BaseApplication;
import com.weyee.poscore.di.component.AppComponent;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/3 0003
 */
public class App extends BaseApplication {

    public static AppComponent obtainAppComponentFromContext(Context context) {
        return ((App) context.getApplicationContext()).getAppComponent();
    }
}
