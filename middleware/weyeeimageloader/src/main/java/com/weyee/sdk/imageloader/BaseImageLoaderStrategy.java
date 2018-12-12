package com.weyee.sdk.imageloader;

import android.content.Context;

/**
 * Created by liu-feng on 16/4/15.
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    void loadImage(Context ctx, T config);
    void clearImage(Context ctx, T config);
}
