package com.letion.app.photo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 解决多点触摸和viewpager自身的滑动事件冲突，导致crash： pointerIndex out of range
 * 解决角标越界的问题
 * Created by liu-feng on 2017/12/7.
 */
class MutipleTouchViewPager : ViewPager {
    private var mIsDisallowIntercept = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // keep the info about if the innerViews do
        // requestDisallowInterceptTouchEvent
        mIsDisallowIntercept = disallowIntercept
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // the incorrect array size will only happen in the multi-touch
        // scenario.
        if (ev.pointerCount > 1 && mIsDisallowIntercept) {
            requestDisallowInterceptTouchEvent(false)
            val handled = super.dispatchTouchEvent(ev)
            requestDisallowInterceptTouchEvent(true)
            return handled
        } else {
            return super.dispatchTouchEvent(ev)
        }
    }
}
