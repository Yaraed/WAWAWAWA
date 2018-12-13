package com.weyee.sdk.imageloader.glide;

import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/13 0013
 */
@com.bumptech.glide.annotation.GlideExtension
public class GlideExtension {
    /**
     * 将这个类的构造函数声明成private，这是必须要求的写法
     */
    private GlideExtension() {
    }

    @GlideOption
    public static void glideConfiguration(RequestOptions options) {
        //占位图
        //options.placeholder(R.drawable.ic_launcher_background);
        //错误图片
        //options.error(R.mipmap.load_error);
        //表示只缓存原始图片
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
        //切圆
        //options.circleCrop();
        //...等等所有的属性
    }
}
