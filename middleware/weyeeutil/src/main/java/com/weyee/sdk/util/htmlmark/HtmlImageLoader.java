package com.weyee.sdk.util.htmlmark;

import android.graphics.Bitmap;
import androidx.annotation.DrawableRes;

import java.util.List;

/**
 * 图片加载，可自定义图片加载框架
 * Created by liu-feng on 2017/11/30.
 */
public interface HtmlImageLoader {
    /**
     * 图片加载回调
     */
    interface Callback {
        /**
         * 加载成功
         */
        void onLoadComplete(Bitmap bitmap);

        /**
         * 加载失败
         */
        void onLoadFailed();
    }

    /**
     * 加载图片
     */
    void loadImage(String url, Callback callback);

    /**
     * 加载中的占位图
     */
    @DrawableRes int getDefaultDrawable();

    /**
     * 加载失败的占位图
     */
    @DrawableRes int getErrorDrawable();

    /**
     * 图片最大宽度，即TextView最大宽度
     */
    int getMaxWidth();

    /**
     * 是否强制将图片等比例拉伸到最大宽度<br>
     * 如果返回true，则需要指定{@link #getMaxWidth()}
     */
    boolean fitWidth();

    /**
     * 点击图片进行跳转
     * @param index
     * @param urls
     */
    void imageClick(int index, List<String> urls);
}
