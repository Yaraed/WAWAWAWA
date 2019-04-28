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

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.base.ThreadPool
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter
import kotlinx.android.synthetic.main.activity_gpu.*

@Route(path = Path.GPU + "GPU")
class GPUActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun getResourceId(): Int = R.layout.activity_gpu

    override fun initView(savedInstanceState: Bundle?) {
        ThreadPool.run {
            val input = assets.open("test.jpeg")
            val bmp = BitmapFactory.decodeStream(input)

            val gpuImage = GPUImage(this@GPUActivity)
            gpuImage.setImage(bmp)
            gpuImage.setFilter(GPUImageGrayscaleFilter())
            val resultBmp1 = gpuImage.bitmapWithFilterApplied

            runOnUiThread {
                imageView1.setImageBitmap(resultBmp1)
            }

            gpuImage.setImage(Uri.parse("https://img-blog.csdn.net/20160821193219835?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center"))
            gpuImage.setFilter(GPUImageBoxBlurFilter(3f))
            val resultBmp2 = gpuImage.bitmapWithFilterApplied

            runOnUiThread {
                imageView2.setImageBitmap(resultBmp2)
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}
