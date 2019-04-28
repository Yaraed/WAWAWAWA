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
import android.view.LayoutInflater
import com.weyee.sdk.dialog.BaseDialog
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import kotlinx.android.synthetic.main.dialog_blur.*

/**
 *
 * @author wuqi by 2019/2/14.
 */
class BlurDialog(context: Context) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_blur, null)
        setContentView(inflate)
        setViewLocation()
        App.obtainAppComponentFromContext(context).imageLoader()
            .loadImage(context, GlideImageConfig.builder().blurValue(20).resource("http://img.weyee.com/weyee_score_2016_20180425151306753282").imageView(blurred_view).build())
    }
}