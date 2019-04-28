package com.weyee.sdk.imageloader.glide;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.weyee.sdk.imageloader.progress.OnRequestListener;

/**
 * @author wuqi by 2019/4/25.
 */
public class GlideSimpleTarget<Z> extends SimpleTarget<Z> {

    private OnRequestListener<Z> listener;

    public GlideSimpleTarget(OnRequestListener<Z> listener) {
        super();
        this.listener = listener;
    }

    @Override
    public void onResourceReady(@NonNull Z resource, @Nullable Transition<? super Z> transition) {
        if (listener != null) listener.onResourceReady(resource);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        if (listener != null) listener.onLoadFailed();
        super.onLoadFailed(errorDrawable);
    }
}
