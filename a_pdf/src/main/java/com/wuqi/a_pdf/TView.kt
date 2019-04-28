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
import android.view.View
import com.blankj.utilcode.util.SizeUtils

/**
 *
 * @author wuqi by 2019/3/5.
 */
class TView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mPaint: Paint = Paint()
    private var mWidth: Int = 0
    private var mHeight: Int = 0


    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#333333")
        mPaint.textSize = SizeUtils.dp2px(14f).toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.save()
        //canvas?.drawColor(Color.WHITE)
        //canvas?.translate(20f, 20f)
        canvas?.drawText(TView::class.java.simpleName, 0f, 20f, mPaint)

        //canvas?.translate(0f, 80f)

        mPaint.color = Color.GREEN
        canvas?.drawPosText(
            "SLOOP",
            floatArrayOf(
                0f, 0f,
                30f, 30f,
                60f, 60f,
                90f, 90f,
                120f, 120f
            ), mPaint
        )

        canvas?.restore()
        canvas?.save()
        canvas?.translate(mWidth / 2f, mHeight / 2f)


        val path1 = Path()
        path1.lineTo(100f, 100f)
        path1.lineTo(0f, 100f)

        path1.close()

        mPaint.strokeWidth = 3f
        canvas?.drawPath(path1, mPaint)

        val path2 = Path()
        path2.addRect(RectF(-200f, -200f, 200f, 200f), Path.Direction.CCW)
        path2.setLastPoint(100f, -100f)

        path2.addPath(path1,-100f,-100f)
        canvas?.drawPath(path2, mPaint)

        canvas?.restore()
    }
}