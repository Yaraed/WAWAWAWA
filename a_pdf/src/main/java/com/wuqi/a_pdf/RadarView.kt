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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.SizeUtils

/**
 * 蜘蛛网雷达
 * @author wuqi by 2019/3/5.
 */
class RadarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val mPaint: Paint = Paint()
    private val mTextPaint: Paint = TextPaint()
    private val mValuePaint: Paint = Paint()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val titles = arrayListOf("身份", "人脉", "历史", "行为", "履约能力", "年龄")
    private var data = doubleArrayOf(100.0, 60.0, 43.0, 34.0, 99.0, 87.1)

    init {
        mPaint.strokeWidth = 3f
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE

        mTextPaint.textSize = SizeUtils.dp2px(14f).toFloat()
        mTextPaint.color = Color.LTGRAY

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 绘制蜘蛛网格
        val r = Math.min(mWidth, mHeight) * 0.8f / 2 / (COUNT - 1)
        val path = Path()
        for (i in 1 until COUNT) {
            path.reset()
            for (j in 0 until COUNT) {
                if (j == 0) {
                    path.moveTo(mWidth / 2 + r * i, (mHeight / 2).toFloat())
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    val x = mWidth / 2 + r * i * Math.cos(Math.PI * 2 / COUNT * j)
                    val y = mHeight / 2 + r * i * Math.sin(Math.PI * 2 / COUNT * j)
                    path.lineTo(x.toFloat(), y.toFloat())
                }
            }
            path.close()
            canvas?.drawPath(path, mPaint)
        }
        //绘制直线
        for (i in 0 until COUNT) {
            path.reset()
            path.moveTo(mWidth / 2f, mHeight / 2f)
            val x = mWidth / 2 + r * (COUNT - 1) * Math.cos(Math.PI * 2 / COUNT * i)
            val y = mHeight / 2 + r * (COUNT - 1) * Math.sin(Math.PI * 2 / COUNT * i)
            path.lineTo(x.toFloat(), y.toFloat())
            canvas?.drawPath(path, mPaint)
        }
        // 绘制文字
        val fontMetrics = mTextPaint.fontMetrics
        val fontHeight = fontMetrics.descent - fontMetrics.ascent
        for (i in 0 until COUNT) {
            var x = mWidth / 2 + (r * (COUNT - 1) + fontHeight / 2) * Math.cos(Math.PI * 2 / COUNT * i)
            var y = mHeight / 2 + (r * (COUNT - 1) + fontHeight / 2) * Math.sin(Math.PI * 2 / COUNT * i)

            if (i == 1 || i == 2) {
                y += fontHeight / 2
            }
            if (i == 3) {
                x -= mTextPaint.measureText(titles[i])
            }

            canvas?.drawText(titles[i], x.toFloat(), y.toFloat(), mTextPaint)

        }
        // 绘制阴影部分
        mValuePaint.alpha = 255
        mValuePaint.color = Color.BLUE
        for (i in 0 until COUNT) {
            val percent = data[i] / MAX_VALUE
            val x = mWidth / 2 + r * (COUNT - 1) * Math.cos(Math.PI * 2 / COUNT * i) * percent
            val y = mHeight / 2 + r * (COUNT - 1) * Math.sin(Math.PI * 2 / COUNT * i) * percent
            if (i == 0) {
                path.moveTo(x.toFloat(), mHeight / 2f)
            } else {
                path.lineTo(x.toFloat(), y.toFloat())
            }
            //绘制小圆点
            canvas?.drawCircle(x.toFloat(), y.toFloat(), 10f, mValuePaint)
        }
        mValuePaint.style = Paint.Style.STROKE
        canvas?.drawPath(path, mValuePaint)
        mValuePaint.alpha = 127
        //绘制填充区域
        mValuePaint.style = Paint.Style.FILL_AND_STROKE
        canvas?.drawPath(path, mValuePaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        for (i in 0 until COUNT) {
            data[i] = Math.random() * MAX_VALUE
        }
        invalidate()
        return super.onTouchEvent(event)
    }

    companion object {
        private const val COUNT = 6
        private const val MAX_VALUE = 100
    }
}