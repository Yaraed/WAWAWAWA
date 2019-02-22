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

package com.wuqi.a_intent

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.permission.IntentEnum
import com.weyee.sdk.router.IntentNavigation
import kotlinx.android.synthetic.main.activity_intent.*

@Route(path = IntentNavigation.MODULE_NAME + "Intent")
class IntentActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {

    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,true)

        recyclerView.adapter = IntentAdapter(IntentEnum.values())
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).color(Color.parseColor("#ff0000")).size(10).build())
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getResourceId(): Int = R.layout.activity_intent
}
