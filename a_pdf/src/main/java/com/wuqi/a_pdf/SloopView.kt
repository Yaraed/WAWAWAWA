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
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ScreenUtils

/**
 *
 * @author wuqi by 2019/3/5.
 */
class SloopView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mPaint: Paint = Paint()
    private var isPress: Boolean = false
    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private val colors =
        intArrayOf(
            0xFFCCFF00.toInt(),
            0xFF6495ED.toInt(),
            0xFFE32636.toInt(),
            0xFF808000.toInt(),
            0xFFFF8C69.toInt(),
            0xFF808080.toInt(),
            0xFFE6B800.toInt(),
            0xFF7CFC00.toInt()
        )

    private lateinit var mData: MutableList<Pie>
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var startAngle = 0f


    init {
        mPaint.color = Color.BLACK       //设置画笔颜色
        mPaint.style = Paint.Style.STROKE  //设置画笔模式为填充
        mPaint.strokeWidth = 5f
        mPaint.isAntiAlias = true

        val list = mutableListOf<Pie>()
        list.add(Pie("Google", 100f))
        list.add(Pie("MicroSoft", 200f))
        list.add(Pie("Amazon", 50f))
        list.add(Pie("Apple", 70f))
        list.add(Pie("Alibaba", 152f))
        list.add(Pie("Tencent", 152f))
        setData(list)
    }

    private fun setData(list: MutableList<Pie>?) {
        this.mData = list ?: mutableListOf()
        mData.map {
            val index = mData.indexOf(it)
            it.color = colors[index % colors.size]
            it.value
        }
            .reduce { f, f1 ->
                f + f1
            }.also {
                mData.forEach { pie ->
                    pie.percentage = pie.value / it
                    pie.angle = pie.percentage * 360
                }
            }

        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mWidth = w
        this.mHeight = h
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isPress) {
            mPaint.color = Color.BLUE
        } else {
            mPaint.color = Color.BLACK
        }
        // 绘制点
        canvas?.drawPoint(100f, 100f, mPaint)
        canvas?.drawPoints(floatArrayOf(100f, 120f, 100f, 140f, 100f, 160f), mPaint)
        // 绘制直线
        canvas?.drawLine(120f, 100f, ScreenUtils.getScreenWidth().toFloat() - 100f, 100f, mPaint)
        canvas?.drawLines(
            floatArrayOf(
                120f, 120f, ScreenUtils.getScreenWidth().toFloat() - 100f, 120f,
                120f, 140f, ScreenUtils.getScreenWidth().toFloat() - 100f, 140f,
                120f, 160f, ScreenUtils.getScreenWidth().toFloat() - 100f, 160f
            ), mPaint
        )
        // 绘制矩形，圆角矩形
        mPaint.color = Color.TRANSPARENT
        canvas?.drawRect(RectF(120f, 180f, ScreenUtils.getScreenWidth().toFloat() - 100f, 300f), mPaint)
        mPaint.color = Color.RED
        canvas?.drawRoundRect(RectF(120f, 180f, ScreenUtils.getScreenWidth().toFloat() - 100f, 300f), 20f, 20f, mPaint)
        // 绘制椭圆
        mPaint.color = Color.BLACK
        canvas?.drawOval(RectF(120f, 320f, ScreenUtils.getScreenWidth().toFloat() - 100f, 500f), mPaint)
        // 绘制圆
        mPaint.color = Color.BLUE
        canvas?.drawCircle(ScreenUtils.getScreenWidth() / 2f, 620f, 100f, mPaint)
        // 绘制圆弧
        // TODO
        canvas?.save()

        // 绘制饼图
        mPaint.style = Paint.Style.FILL
        val r = (Math.min(mWidth, mHeight) / 2 * 0.5).toFloat()  // 饼状图半径
        canvas?.translate((mWidth / 2).toFloat(), mHeight.toFloat() - r - 20f)
        val rectF = RectF(-r, -r, r, r)
        //canvas?.scale(0.75f,0.75f)
        mData.forEach {
            mPaint.color = it.color
            canvas?.drawArc(rectF, startAngle, it.angle, true, mPaint)
            startAngle += it.angle
        }
        canvas?.restore()
        canvas?.save()
        canvas?.translate(20f, mHeight.toFloat() - 200f)
        //canvas?.rotate(180f)
        canvas?.scale(0.5f, 0.5f)
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        canvas?.drawRect(0f, 0f, ScreenUtils.getScreenWidth().toFloat() - 100f, 180f, mPaint)
        canvas?.restore()
        canvas?.save()
        mPaint.strokeWidth = 3f
        mPaint.color = colors[colors.size - 1]
        canvas?.translate(220f, mHeight.toFloat() - 420f)
        val rectF1 = RectF(-200f, -200f, 200f, 200f)
        for (i in 0..50) {
            canvas?.drawRect(rectF1, mPaint)
            canvas?.scale(0.9f, 0.9f)
        }

        canvas?.restore()
        canvas?.save()
        canvas?.translate(mWidth-220f,mHeight-420f)
        mPaint.color = Color.BLACK
        canvas?.drawCircle(0f,0f,200f,mPaint)
        canvas?.drawCircle(0f,0f,180f,mPaint)
        for (i in 0..36){
            canvas?.drawLine(0f,180f,0f,200f,mPaint)
            canvas?.rotate(10f)
        }
    }

//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//
//        when {
//            event?.action == MotionEvent.ACTION_DOWN -> {
//                isPress = true
//                // 必须更新点东西，否则invalidate不会重新绘制
//                setBackgroundColor(Color.TRANSPARENT)
//                invalidate()
//            }
//            event?.action == MotionEvent.ACTION_UP || event?.action == MotionEvent.ACTION_CANCEL -> {
    // 这里始终不会回调，不知道为什么
//                isPress = false
//                setBackgroundColor(Color.WHITE)
//                invalidate()
//            }
//        }
//        return super.onTouchEvent(event)
//    }

    data class Pie(
        val name: String,
        val value: Float,
        var percentage: Float = 0f,
        var color: Int = 0,
        var angle: Float = 0f
    )
}