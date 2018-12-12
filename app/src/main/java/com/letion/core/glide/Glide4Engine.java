package com.letion.core.glide;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/6 0006
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.weyee.sdk.imageloader.ImageLoader;
import com.weyee.sdk.imageloader.glide.GlideImageConfig;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * {@link ImageEngine} implementation using Glide.
 */
public class Glide4Engine implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        ImageLoader.getInstance().loadImage(context,GlideImageConfig.builder().url(uri.getPath())
                .imageView(imageView)
                .build());
    }

    @Override
    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri
            uri) {
        ImageLoader.getInstance().loadImage(context,GlideImageConfig.builder().url(uri.getPath())
                .imageView(imageView)
                .build());
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        ImageLoader.getInstance().loadImage(context,GlideImageConfig.builder().url(uri.getPath())
                .imageView(imageView)
                .build());
    }

    @Override
    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        ImageLoader.getInstance().loadImage(context,GlideImageConfig.builder().url(uri.getPath())
                .imageView(imageView)
                .build());
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

}
