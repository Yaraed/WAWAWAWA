package com.weyee.sdk.imageloader;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liu-feng on 16/4/15.
 */
@Singleton
public final class ImageLoader {
    private BaseImageLoaderStrategy<? super ImageConfig> mStrategy;

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

    /**
     * 可动态修改ImageLoader的实现方式，例如使用Picasso代替Glide
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy<? super ImageConfig> strategy) {
        this.mStrategy = strategy;
    }

}
