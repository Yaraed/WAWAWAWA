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

package com.letion.app.photo

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.letion.app.App
import com.letion.app.R
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.router.Path
import kotlinx.android.synthetic.main.activity_pre_view.*

@Route(path = Path.MAIN + "PreView")
class PreViewActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_pre_view

    override fun initView(savedInstanceState: Bundle?) {
        App.obtainAppComponentFromContext(context)
            .imageLoader().loadImage(
                context, GlideImageConfig.builder()
                    .isCrossFade(true)
                    //.imageRadius(10)
                    .imageView(photoView)
                    //.blurValue(15)
                    .isCircle(true)
                    .resource("http://youimg1.c-ctrip.com/target/tg/655/120/973/b1d405cc68214e7da7a8293183abd2c8.jpg")
                    .listener { isComplete, percentage, _, _ ->
                        if (isComplete) {
                            progressBar.visibility = View.GONE
                        }
                        progressBar.progress = percentage
                    }
                    .build()
            )

    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}
