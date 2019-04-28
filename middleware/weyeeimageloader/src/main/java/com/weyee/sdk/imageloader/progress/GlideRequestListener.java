package com.weyee.sdk.imageloader.progress;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;

/**
 * @author wuqi by 2019/4/15.
 */
public class GlideRequestListener<R> implements com.bumptech.glide.request.RequestListener<R> {
    private OnRequestListener<R> listener;

    public GlideRequestListener(OnRequestListener<R> listener) {
        this.listener = listener;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        return listener != null && listener.onLoadFailed();
    }

    @Override
    public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
        return listener != null && listener.onResourceReady(resource);
    }


}
