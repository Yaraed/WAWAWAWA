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

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_canvas.*

@Route(path = Path.PDF + "Canvas")
class CanvasActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_canvas

    override fun initView(savedInstanceState: Bundle?) {
        radarView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        control_menu.setListener(object : RemoteControlMenu.MenuListener{
            override fun onCenterCliched() {
                ToastUtils.show("Center")
            }

            override fun onUpCliched() {
            }

            override fun onRightCliched() {
            }

            override fun onDownCliched() {
            }

            override fun onLeftCliched() {
            }

        })
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}
