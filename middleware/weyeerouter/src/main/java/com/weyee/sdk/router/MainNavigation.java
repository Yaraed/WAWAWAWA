package com.weyee.sdk.router;

import android.content.Context;
import android.os.Bundle;

/**
 * main模块跳转导航管理类。
 */
public class MainNavigation extends Navigation {

    public static final String MODULE_NAME = "/main/";

    public MainNavigation(Context context) {
        super(context);
    }

    /**
     * 配置Module
     */
    @Override
    protected String getModuleName() {
        return MODULE_NAME;
    }

    public void toTranslucentActivity(int activityAnimStyle) {
        startActivity(activityAnimStyle, "Translucent");
    }

    public void toPhotoViewActivity(String[] urls) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("urls", urls);
        startActivity("PhotoView", bundle);
    }

    public void toImageViewActivity() {
        startActivity("PImageView");
    }
    public void toSpinnerActivity() {
        startActivity("Spinner");
    }
    public void toPreViewActivity() {
        startActivity("PreView");
    }
}
