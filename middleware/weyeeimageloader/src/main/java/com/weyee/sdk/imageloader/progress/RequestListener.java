package com.weyee.sdk.imageloader.progress;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;

/**
 * @author wuqi by 2019/4/15.
 */
public abstract class RequestListener<R> implements com.bumptech.glide.request.RequestListener<R> {
    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        onLoadFailed();
        return false;
    }

    @Override
    public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
        return onResourceReady(resource);
    }

    public abstract void onLoadFailed();

    public abstract boolean onResourceReady(R resource);

}
