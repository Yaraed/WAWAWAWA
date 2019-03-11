/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.wuqi.a_pdf

import android.content.Context
import android.graphics.Matrix
import android.os.Debug
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView

/**
 * 可自由缩放，多指操作de ImageView
 * @author wuqi by 2019/3/6.
 */
class ZoomImageView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs),
    ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private var isViewCreated: Boolean = false

    private var initScale: Float = 1.0f
    private var minScale: Float = 1.0f
    private var maxScale: Float = 1.0f

    private var zoomMatrix = Matrix()
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    init {
        // 不管使用该View传递什么scaleType，都始终用MATRIX渲染
        scaleType = ScaleType.MATRIX
        scaleGestureDetector = ScaleGestureDetector(context, this)
        setOnTouchListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onDetachedFromWindow() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        super.onDetachedFromWindow()
    }

    override fun onGlobalLayout() {
        if (!isViewCreated) {
            val drawable = drawable ?: return

            val w = width
            val h = height

            val dw = drawable.intrinsicWidth
            val dh = drawable.intrinsicHeight

            // 计算图片缩放比例
            var scale = 1.0f

            if ((dw > w && dh > h) || (dw < w && dh < h)) {
                scale = Math.min(w * 1.0f / dw, h * 1.0f / dh)
            } else if (dw > w && dh < h) {
                scale = w * 1.0f / dw
            } else if (dw < w && dh > h) {
                scale = h * 1.0f / dh
            }

            initScale = scale
            minScale = scale / 2
            maxScale = scale * 4

            // 将图片居中
            val dx = w / 2f - dw / 2f
            val dy = h / 2f - dh / 2f

            zoomMatrix.postTranslate(dx, dy)
            zoomMatrix.postScale(initScale, initScale, w / 2f, h / 2f)

            imageMatrix = zoomMatrix


            isViewCreated = true
        }
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (drawable == null) {
            return true
        }

        val values = FloatArray(9)
        zoomMatrix.getValues(values)

        val current = values[Matrix.MSCALE_X]
        val change = detector?.scaleFactor ?: 1.0f


        if (current > maxScale && change > 1.0f) return true
        if (current < minScale && change < 1.0f) return true

        zoomMatrix.postScale(change, change, detector?.focusX ?: width / 2f, detector?.focusY ?: height / 2f)
        imageMatrix = zoomMatrix

        return true
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

}