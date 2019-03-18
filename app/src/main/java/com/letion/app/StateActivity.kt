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

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.letion.app.state.LoadingState
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.poswidget.stateview.StateLayout
import com.weyee.poswidget.stateview.state.ContentState
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.DefaultAdapter
import com.weyee.sdk.router.Path
import kotlinx.android.synthetic.main.activity_state.*

/**
 * 显示各种状态的View
 */
@Route(path = Path.MAIN + "State")
class StateActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_state

    override fun initView(savedInstanceState: Bundle?) {
        stateLayout.stateManager.addState(LoadingState())
        stateLayout.showState(LoadingState.STATE)
    }

    override fun initData(savedInstanceState: Bundle?) {
        recyclerView.postDelayed({
            stateLayout.showState(ContentState.STATE)
            recyclerView.layoutManager = LinearLayoutManager(this@StateActivity)

            recyclerView.adapter = object :
                DefaultAdapter<String>(mutableListOf("liufegn", "zhangsan", "lisi", "wangwu", "zhaoliu", "sunqi")) {
                override fun getHolder(v: View, viewType: Int): BaseHolder<String> {
                    return object : BaseHolder<String>(v) {
                        override fun setData(data: String, position: Int) {
                            if (viewType != 0) {
                                val stateLayout = itemView.findViewById<StateLayout>(R.id.stateLayout)
                                stateLayout.stateManager.addState(LoadingState())
                                stateLayout.showState(LoadingState.STATE)
                                stateLayout.postDelayed({
                                    stateLayout.showState { ContentState.STATE }
                                }, 3000)
                                itemView.findViewById<TextView>(R.id.textView).text = data
                            } else {
                                (itemView as TextView).text = data
                            }
                        }

                    }
                }

                override fun getLayoutId(viewType: Int): Int {
                    return if (viewType == 0) android.R.layout.simple_list_item_1 else R.layout.item_stateview
                }

                override fun getItemViewType(position: Int): Int {
                    return if (position != 2) super.getItemViewType(position) else 1
                }

            }
        }, 3000)
    }
}
