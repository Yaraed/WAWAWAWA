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

package com.weyee.poswidget.textview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义跑马灯Textview类
 *
 * @author xuexiang
 * @since 2018/11/22 上午12:36
 */
public class AutoMoveTextView extends AppCompatTextView {
	public AutoMoveTextView(Context context) {
		super(context);
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
	}

	public AutoMoveTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
	}

	public AutoMoveTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
