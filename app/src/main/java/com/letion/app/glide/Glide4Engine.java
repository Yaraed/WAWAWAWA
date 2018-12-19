package com.letion.app.glide;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/6 0006
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.letion.app.App;
import com.weyee.sdk.imageloader.glide.GlideImageConfig;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * {@link ImageEngine} implementation using Glide.
 */
public class Glide4Engine implements ImageEngine {

    //简易处理板  （实际本没有发现什么问题，可以直接使用）
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        ((App) context.getApplicationContext()).getAppComponent().imageLoader().loadImage(context, GlideImageConfig.builder().resource(uri)
                .imageView(imageView)
                .build());
    }

    @Override
    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri
            uri) {
        ((App) context.getApplicationContext()).getAppComponent().imageLoader().loadImage(context, GlideImageConfig.builder().resource(uri)
                .imageView(imageView)
                .build());
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        ((App) context.getApplicationContext()).getAppComponent().imageLoader().loadImage(context, GlideImageConfig.builder().resource(uri)
                .imageView(imageView)
                .build());
    }

    @Override
    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        ((App) context.getApplicationContext()).getAppComponent().imageLoader().loadImage(context, GlideImageConfig.builder().resource(uri)
                .imageView(imageView)
                .build());
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

}
