package com.weyee.sdk.router;

import android.content.Context;
import android.os.Bundle;

/**
 * main模块跳转导航管理类。
 */
public class MainNavigation extends Navigation {

    private static final String MODULE_NAME = Path.MAIN;

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
        toPhotoViewActivity(0, urls);
    }

    public void toPhotoViewActivity(int index, String[] urls) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
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

    public void toStateActivity() {
        startActivity("State");
    }
}
