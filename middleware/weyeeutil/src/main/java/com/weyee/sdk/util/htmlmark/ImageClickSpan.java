package com.weyee.sdk.util.htmlmark;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


/**
 * Created by liu-feng on 2017/5/5.
 */
final class ImageClickSpan extends ClickableSpan {
    private List<String> imageUrls;
    private int position;
    private HtmlImageLoader loader;

    public ImageClickSpan(List<String> imageUrls, int position, @Nullable HtmlImageLoader loader) {
        this.imageUrls = imageUrls;
        this.position = position;
        this.loader = loader;
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (loader != null) {
            loader.imageClick(position, imageUrls);
        }
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
