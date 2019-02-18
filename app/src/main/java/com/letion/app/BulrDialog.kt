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

package com.letion.app

import android.content.Context
import android.os.Bundle
import com.weyee.sdk.dialog.BaseDialog
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import kotlinx.android.synthetic.main.dialog_blur.*

/**
 *
 * @author wuqi by 2019/2/14.
 */
class BulrDialog(context: Context) : BaseDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_blur)
        blurring_view.setBlurredView(blurred_view)
        App.obtainAppComponentFromContext(context).imageLoader()
            .loadImage(context, GlideImageConfig.builder().imageView(blurred_view).build())
    }
}