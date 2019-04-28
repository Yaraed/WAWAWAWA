package com.weyee.sdk.util.htmlmark;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu-feng on 2017/11/30.
 */
public class HtmlMark {
    private String source;
    private HtmlImageLoader loader;

    private HtmlMark(@Nullable String source, @Nullable HtmlImageLoader loader) {
        this.source = source;
        this.loader = loader;
    }

    /**
     * 设置源文本
     */
    public static HtmlMark from(@Nullable String source, @Nullable HtmlImageLoader loader) {
        return new HtmlMark(source, loader);
    }

    /**
     * 注入TextView
     */
    public void into(TextView textView) {
        if (TextUtils.isEmpty(source)) {
            textView.setText("");
            return;
        }

        HtmlImageGetter imageGetter = new HtmlImageGetter();
        imageGetter.setTextView(textView);
//        imageGetter.setImageLoader(new HtmlImageLoader() {
//            @Override
//            public void loadImage(String url, Callback callback) {
//                GlideMiracle.with(textView.getContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
//
//
//                    @Override
//                    public void onResourceReady(Bitmap resource, Transition<? super
//                            Bitmap> transition) {
//                        callback.onLoadComplete(resource);
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        callback.onLoadFailed();
//                    }
//                });
//            }
//
//            @Override
//            public Drawable getDefaultDrawable() {
//                return ContextCompat.getDrawable(textView.getContext(), R.mipmap.default_placeholder);
//            }
//
//            @Override
//            public Drawable getErrorDrawable() {
//                return ContextCompat.getDrawable(textView.getContext(), R.mipmap.default_fail);
//            }
//
//            @Override
//            public int getMaxWidth() {
//                DisplayMetrics dm = textView.getContext().getResources().getDisplayMetrics();
//                return dm.widthPixels - textView.getPaddingLeft() - textView.getPaddingRight();
//            }
//
//            @Override
//            public boolean fitWidth() {
//                return true;
//            }
//        });
        imageGetter.setImageLoader(loader);
        imageGetter.getImageSize(source);

        HtmlTagHandler tagHandler = new HtmlTagHandler();
        tagHandler.setTextView(textView);
        source = tagHandler.overrideTags(source);

        Spanned spanned = Html.fromHtml(source, imageGetter, tagHandler);

        List<String> imageUrls = new ArrayList<>();
        SpannableStringBuilder ssb;
        if (spanned instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) spanned;
        } else {
            ssb = new SpannableStringBuilder(spanned);
        }

        // Hold image url link
        imageUrls.clear();
        ImageSpan[] imageSpans = ssb.getSpans(0, ssb.length(), ImageSpan.class);
        for (int i = 0; i < imageSpans.length; i++) {
            ImageSpan imageSpan = imageSpans[i];
            String imageUrl = imageSpan.getSource();
            int start = ssb.getSpanStart(imageSpan);
            int end = ssb.getSpanEnd(imageSpan);
            imageUrls.add(imageUrl);

            ImageClickSpan imageClickSpan = new ImageClickSpan(imageUrls, i,loader);
            ClickableSpan[] clickableSpans = ssb.getSpans(start, end, ClickableSpan.class);
            if (clickableSpans != null) {
                for (ClickableSpan cs : clickableSpans) {
                    ssb.removeSpan(cs);
                }
            }
            ssb.setSpan(imageClickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(ssb);
    }
}
