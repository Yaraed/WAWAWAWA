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
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.letion.app.App
import com.letion.app.R
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.DefaultAdapter
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.router.MainNavigation
import kotlinx.android.synthetic.main.activity_photo_view.*

@Route(path = MainNavigation.MODULE_NAME + "PImageView")
class PhotoViewActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_photo_view

    override fun initView(savedInstanceState: Bundle?) {
        val urls = arrayOf(
            "http://img.weyee.com/weyee_score_2016_20180425151306753282",
            "http://img.weyee.com/weyee_score_2016_20180425151307495086",
            "http://img.weyee.com/weyee_score_2016_20180906104526564400"
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration.Builder(context).size(1).build())
        recyclerView.adapter = object : DefaultAdapter<String>(urls.asList()) {
            override fun getHolder(v: View, viewType: Int): BaseHolder<String> {
                return object : BaseHolder<String>(v) {
                    override fun setData(data: String, position: Int) {
                        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
                        val textView = itemView.findViewById<TextView>(R.id.textView)
                        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)

                        App.obtainAppComponentFromContext(itemView.context)
                            .imageLoader().loadImage(
                                itemView.context, GlideImageConfig.builder()
                                    .isCrossFade(true)
                                    //.imageRadius(10)
                                    .imageView(imageView)
                                    //.blurValue(15)
                                    .isCircle(true)
                                    .resource(data)
                                    .listener { isComplete, percentage, _, _ ->
                                        if (isComplete) {
                                            progressBar.visibility = View.GONE
                                        }
                                        progressBar.progress = percentage
                                    }
                                    .build()
                            )

                        textView.text = data

                    }
                }
            }

            override fun getLayoutId(viewType: Int): Int = R.layout.item_imageview

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}
