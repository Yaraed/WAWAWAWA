package com.wuqi.a_service

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.customview.widget.ViewDragHelper
import com.weyee.poswidget.layout.QMUIFrameLayout

/**
 *
 * @author wuqi by 2019/4/26.
 */
class AdjustFrameLayout : QMUIFrameLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attributes: AttributeSet?) : super(context, attributes)

    private var viewDragHelper: ViewDragHelper? = null

    init {
        viewDragHelper = ViewDragHelper.create(this, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child.id == R.id.layout
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }

        })
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { viewDragHelper?.processTouchEvent(it) }
        return true
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return viewDragHelper?.shouldInterceptTouchEvent(event!!) ?: super.onTouchEvent(event)
    }
}