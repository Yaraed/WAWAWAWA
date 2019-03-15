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

package com.wuqi.a_gpuimage

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.App
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.medialoader.MediaLoader
import com.weyee.sdk.medialoader.bean.AudioItem
import com.weyee.sdk.medialoader.bean.AudioResult
import com.weyee.sdk.medialoader.callback.OnAudioLoaderCallBack
import com.weyee.sdk.medialoader.filter.AudioFilter
import com.weyee.sdk.medialoader.filter.PhotoFilter
import com.weyee.sdk.medialoader.filter.VideoFilter
import com.weyee.sdk.multitype.*
import com.weyee.sdk.permission.PermissionIntents
import com.weyee.sdk.router.MainNavigation
import com.weyee.sdk.router.Path
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_media.*

/**
 * 获取媒体文件
 */
@Route(path = Path.GPU + "Media")
class MediaActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {

    private var photoFilter: PhotoFilter? = null
    private var videoFilter: VideoFilter? = null
    private var audioFilter: AudioFilter? = null
    private var list: MutableList<AudioItem>? = null
    private val path: MutableList<String> = mutableListOf()

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_media

    override fun initView(savedInstanceState: Bundle?) {

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(this@MediaActivity).color(Color.TRANSPARENT).size(
                10
            ).build()
        )
        recyclerView.addItemDecoration(
            VerticalDividerItemDecoration.Builder(this@MediaActivity).color(Color.TRANSPARENT).size(
                10
            ).build()
        )

        recyclerView.adapter =
            object : DefaultAdapter<AudioItem>(list,
                OnRecyclerViewItemClickListener<AudioItem> { _, _, data, _ ->
                    MainNavigation(this@MediaActivity).toPhotoViewActivity(path.indexOf(data.path), path.toTypedArray())
                }) {
                override fun getHolder(v: View, viewType: Int): BaseHolder<AudioItem> {
                    return object : BaseHolder<AudioItem>(v) {
                        override fun setData(data: AudioItem, position: Int) {
                            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
                            val textView = itemView.findViewById<TextView>(R.id.textView)
                            (itemView.context.applicationContext as App).appComponent.imageLoader()
                                .loadImage(
                                    itemView.context,
                                    GlideImageConfig.builder().resource(R.mipmap.ic_back)
                                        .imageRadius(10)
                                        .imageView(imageView).build()
                                )

                            textView.text = data.displayName
                        }

                    }
                }

                override fun getLayoutId(viewType: Int): Int = R.layout.item_image

            }


    }

    override fun initData(savedInstanceState: Bundle?) {
        AndPermission.with(this@MediaActivity).runtime()
            .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .onGranted {
                MediaLoader.getLoader().loadAudios(this@MediaActivity, object : OnAudioLoaderCallBack() {
                    override fun onResult(result: AudioResult?) {
                        (recyclerView.adapter as DefaultAdapter<*>).data = result?.items
                        result?.items?.forEach {
                            path.add(it.path)
                        }
                    }
                })
            }.onDenied {
                PermissionIntents.toPermissionSetting(this@MediaActivity)
            }.start()

    }

}
