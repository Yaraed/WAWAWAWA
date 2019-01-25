package com.weyee.sdk.imageloader;

import android.widget.ImageView;

/**
 * Created by liu-feng on 16/4/15.
 * 这里是图片加载配置信息的基类,可以定义一些所有图片加载框架都可以用的通用参数
 */
public class ImageConfig {
    protected Object resource;
    protected ImageView imageView;
    protected int placeholder;
    protected int errorPic;

    /**
     * @return 支持：
     * Bitmap
     * Drawable
     * String
     * Uri
     * File
     * RawRes DrawableRes
     * URL
     * byte[]
     */
    public Object getResource() {
        return resource;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
