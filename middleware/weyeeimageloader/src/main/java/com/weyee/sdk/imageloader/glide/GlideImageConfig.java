package com.weyee.sdk.imageloader.glide;

import android.graphics.Bitmap;
import android.widget.ImageView;
import androidx.annotation.FloatRange;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.weyee.sdk.imageloader.BaseImageLoaderStrategy;
import com.weyee.sdk.imageloader.ImageConfig;
import com.weyee.sdk.imageloader.ImageLoader;
import com.weyee.sdk.imageloader.progress.OnProgressListener;
import com.weyee.sdk.imageloader.progress.OnRequestListener;

/**
 * Created by liu-feng on 16/4/15.
 * 这里放Glide专属的配置信息,可以一直扩展字段,如果外部调用时想让图片加载框架
 * 做一些操作,比如清除或则切换缓存策略,则可以定义一个int类型的变量,内部根据int做不同过的操作
 * 其他操作同理
 */
public class GlideImageConfig<T> extends ImageConfig {
    private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    /**
     * 给图片添加 Glide 独有的 BitmapTransformation
     * <p>
     * 因为 BitmapTransformation 是 Glide 独有的类, 所以如果 BitmapTransformation 出现在 {@link GlideImageConfig} 中
     * 会使 {@link ImageLoader} 难以切换为其他图片加载框架, 在 {@link GlideImageConfig} 中只能配置基础类型和 Android 包里的类
     * 此 API 会在后面的版本中被删除, 请使用其他 API 替代
     *
     * @param transformation {@link BitmapTransformation}
     * @deprecated 请使用 {@link #isCircle()}, {@link #isCenterCrop()}, {@link #isImageRadius()} 替代
     * 如果有其他自定义 BitmapTransformation 的需求, 请自行扩展 {@link BaseImageLoaderStrategy}
     */
    private Transformation<Bitmap> transformation;//glide用它来改变图形的形状
    private Target[] targets;
    private ImageView[] imageViews;
    private boolean isClearMemory;//清理内存缓存
    private boolean isClearDiskCache;//清理本地缓存
    private int fallback; //请求 url 为空,则使用此图片作为占位符

    // 2019.2更新功能
    private boolean isCrossFade;//是否使用淡入淡出过渡动画
    private boolean isCenterCrop;//是否将图片剪切为 CenterCrop
    private boolean isCircle;//是否将图片剪切为圆形
    private int imageRadius;//图片每个圆角的大小
    private int blurValue;//高斯模糊值, 值越大模糊效果越大

    // 2019.3更新功能  进度监听（资源类型只支持URL）
    private OnProgressListener progressListener;

    // 2019.4更新功能 支持缩略图，支持监听加载成功和失败的回调
    private float thumbnail;
    private OnRequestListener<T> listener;


    private GlideImageConfig(Builder builder) {
        this.resource = builder.resource;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.targets = builder.targets;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
        this.fallback = builder.fallback;

        this.isCrossFade = builder.isCrossFade;
        this.isCenterCrop = builder.isCenterCrop;
        this.isCircle = builder.isCircle;
        this.imageRadius = builder.imageRadius;
        this.blurValue = builder.blurValue;

        this.progressListener = builder.progress;

        this.thumbnail = builder.thumbnail;
        this.listener = builder.listener;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public Transformation<Bitmap> getTransformation() {
        return transformation;
    }

    public Target[] getTargets() {
        return targets;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public int getFallback() {
        return fallback;
    }

    public int getBlurValue() {
        return blurValue;
    }

    public boolean isBlurImage() {
        return blurValue > 0;
    }

    public int getImageRadius() {
        return imageRadius;
    }

    public boolean isImageRadius() {
        return imageRadius > 0;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public OnProgressListener getProgressListener() {
        return progressListener;
    }

    public float thumbnail() {
        return thumbnail;
    }

    public OnRequestListener<T> listener() {
        return listener;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder<T> {
        private Object resource;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        private int fallback; //请求 url 为空,则使用此图片作为占位符
        private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        private Transformation<Bitmap> transformation;//glide用它来改变图形的形状
        private Target[] targets;
        private ImageView[] imageViews;
        private boolean isClearMemory;//清理内存缓存
        private boolean isClearDiskCache;//清理本地缓存

        // 2019.2新增功能
        private int imageRadius;//图片每个圆角的大小
        private int blurValue;//高斯模糊值, 值越大模糊效果越大
        private boolean isCrossFade;//是否使用淡入淡出过渡动画
        private boolean isCenterCrop;//是否将图片剪切为 CenterCrop
        private boolean isCircle;//是否将图片剪切为圆形

        // 2019.4新增功能
        private float thumbnail;
        private OnRequestListener<T> listener;

        // 2019.3进度监听
        private OnProgressListener progress;

        private Builder() {
        }

        public Builder resource(Object resource) {
            this.resource = resource;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder transformation(Transformation<Bitmap> transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder targets(Target... targets) {
            this.targets = targets;
            return this;
        }

        public Builder imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public Builder imageRadius(int imageRadius) {
            this.imageRadius = imageRadius;
            return this;
        }

        public Builder blurValue(int blurValue) { //blurValue 建议设置为 15
            this.blurValue = blurValue;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder isCenterCrop(boolean isCenterCrop) {
            this.isCenterCrop = isCenterCrop;
            return this;
        }

        public Builder isCircle(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        public Builder progress(OnProgressListener progress) {
            this.progress = progress;
            return this;
        }

        public Builder thumbnail(@FloatRange(from = 0.0f, to = 1.0f) float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        /**
         * 注意：需要使用listener的地方，必须新增依赖
         * <code>
         *     compileOnly(libsConfig.glide) {
         *         exclude group: "com.android.support"
         *     }
         * </code>
         * 因为Glide库，设计初衷就不打算暴露出去，所以使用该api时，其他module是无法依赖的
         * @param listener
         * @return
         */
        public Builder listener(OnRequestListener<T> listener) {
            this.listener = listener;
            return this;
        }

        public GlideImageConfig build() {
            return new GlideImageConfig(this);
        }
    }
}
