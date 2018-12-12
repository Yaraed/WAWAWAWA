package com.weyee.sdk.imageloader;

import android.content.Context;
import com.weyee.sdk.imageloader.glide.GlideImageLoaderStrategy;

/**
 * Created by liu-feng on 16/4/15.
 */
public final class ImageLoader {
    private static ImageLoader instance;
    private BaseImageLoaderStrategy mStrategy;

    private ImageLoader() {
        this.mStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
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
