package com.weyee.poswidget.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 禁止滑动的ViewPager，且取消默认的滑动动画
 *
 * @author wuqi by 2019/5/7.
 */
public class MViewPager extends ViewPager {
    private boolean noScroll = false;

    public MViewPager(@NonNull Context context) {
        super(context);
    }

    public MViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否能滑动,默认是可以滑动的
     *
     * @return
     */
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (noScroll){
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (noScroll){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 禁止切换ViewPager的默认动画
     * @param item
     */
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}
