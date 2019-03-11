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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * 贝塞尔曲线
 * @author wuqi by 2019/3/6.
 */
class Bezier(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val mPaint: Paint = Paint()

    private lateinit var start: PointF

    private lateinit var end: PointF
    private lateinit var control: PointF

    private var centerX: Float = 0f
    private var centerY: Float = 0f

    init {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f

        start = PointF(0f, 0f)
        end = PointF(0f, 0f)
        control = PointF(0f, 0f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f

        // 初始化数据点和控制点的位置
        start.x = centerX - 200
        start.y = centerY
        end.x = centerX + 200
        end.y = centerY
        control.x = centerX
        control.y = centerY - 100
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        control.x = event!!.x
        control.y = event.y
        invalidate()
        return super.onTouchEvent(event)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 绘制数据点和控制点
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 20f
        canvas?.drawPoint(start.x, start.y, mPaint)
        canvas?.drawPoint(end.x, end.y, mPaint)
        canvas?.drawPoint(control.x, control.y, mPaint)

        // 绘制辅助线
        mPaint.strokeWidth = 4f
        canvas?.drawLine(start.x, start.y, control.x, control.y, mPaint)
        canvas?.drawLine(end.x, end.y, control.x, control.y, mPaint)

        // 绘制贝塞尔曲线
        mPaint.color = Color.RED
        mPaint.strokeWidth = 8f

        val path = Path()

        path.moveTo(start.x, start.y)
        path.quadTo(control.x, control.y, end.x, end.y)

        canvas?.drawPath(path, mPaint)
    }
}