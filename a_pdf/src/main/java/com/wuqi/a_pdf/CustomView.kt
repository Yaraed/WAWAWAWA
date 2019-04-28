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
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * @author wuqi by 2019/3/6.
 */
open class CustomView @JvmOverloads constructor(
    /**
     * the context of current view
     */
    protected var mCurrentContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(mCurrentContext, attrs, defStyleAttr) {

    /**
     * the width of current view.
     */
    protected var mViewWidth: Int = 0

    /**
     * the height of current view.
     */
    protected var mViewHeight: Int = 0

    /**
     * default Paint.
     */
    protected var mDeafultPaint = Paint()

    /**
     * default TextPaint
     */
    protected var mDefaultTextPaint = TextPaint()


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
    }
}
