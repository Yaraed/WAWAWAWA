package com.weyee.sdk.imageloader.progress;

/**
 * @author wuqi by 2019/4/25.
 */
public interface OnRequestListener<R> {
    boolean onLoadFailed();

    boolean onResourceReady(R resource);
}
