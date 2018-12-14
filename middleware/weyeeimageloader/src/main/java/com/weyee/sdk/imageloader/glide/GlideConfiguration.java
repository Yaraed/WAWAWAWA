package com.weyee.sdk.imageloader.glide;

import android.content.Context;
import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by liu-feng on 16/4/15.
 */
@GlideModule
public class GlideConfiguration extends AppGlideModule {
    private static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDiskCache(() -> {
            // Careful: the external cache directory doesn't enforce permissions
            File file = new File(context.getExternalCacheDir(), "glide");
            FileUtils.createOrExistsDir(file);
            return DiskLruCacheWrapper.get(file, IMAGE_DISK_CACHE_MAX_SIZE);
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //Glide默认使用HttpURLConnection做网络请求,在这切换成okhttp请求
        //registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okhttpclient));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return !super.isManifestParsingEnabled();
    }
}