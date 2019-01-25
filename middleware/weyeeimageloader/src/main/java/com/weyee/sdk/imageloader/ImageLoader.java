package com.weyee.sdk.imageloader;

import android.content.Context;
import com.weyee.sdk.imageloader.glide.GlideImageLoaderStrategy;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liu-feng on 16/4/15.
 */
@Singleton
public final class ImageLoader {
    private BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader(BaseImageLoaderStrategy strategy) {
        setLoadImgStrategy(strategy);
    }


    public <T extends ImageConfig> void loadImage(Context context, T config) {
        this.mStrategy.loadImage(context, config);
    }

    public <T extends ImageConfig> void clearImage(Context context, T config) {
        this.mStrategy.clearImage(context, config);
    }


    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }

}
