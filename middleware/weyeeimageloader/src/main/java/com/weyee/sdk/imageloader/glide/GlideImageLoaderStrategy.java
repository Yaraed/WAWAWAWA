package com.weyee.sdk.imageloader.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.Target;

import com.weyee.sdk.imageloader.BaseImageLoaderStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liu-feng on 16/4/15.
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

    @SuppressLint("CheckResult")
    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new IllegalStateException("Context is required");
        if (config == null) throw new IllegalStateException("GlideImageConfig is required");
        // if (TextUtils.isEmpty(config.getUrl())) throw new IllegalStateException("url is
        // required");
        if (config.getImageView() == null) throw new IllegalStateException("imageview is required");


        GlideRequests requests;

        requests = GlideApp.with(ctx);//如果context是activity则自动使用Activity的生命周期

        GlideRequest<Drawable> glideRequest = requests.load(config.getResource())
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop();

        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
        }
        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            glideRequest.transform(config.getTransformation());
        }


        if (config.getPlaceholder() != 0)//设置占位符
            glideRequest.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//设置错误的图片
            glideRequest.error(config.getErrorPic());

        if (config.getFallback() != 0)//设置请求 url 为空图片
            glideRequest.fallback(config.getFallback());


        glideRequest.into(config.getImageView());
    }

    @SuppressLint("CheckResult")
    @Override
    public void clearImage(final Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new IllegalStateException("Context is required");
        if (config == null) throw new IllegalStateException("GlideImageConfig is required");

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                Glide.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        if (config.getTargets() != null && config.getTargets().length > 0) {//取消在执行的任务并且释放资源
            for (Target target : config.getTargets())
                Glide.get(ctx).getRequestManagerRetriever().get(ctx).clear(target);
        }


        if (config.isClearDiskCache()) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(integer -> Glide.get(ctx).clearDiskCache());
        }

        if (config.isClearMemory()) {//清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> Glide.get(ctx).clearMemory());
        }

    }
}
