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

package com.wuqi.a_http

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.HttpNavigation

/**
 * 守护进程的activity
 * 用于后台跑，当应用程序的所有的非守护进程关闭时，所有的守护进程也会关闭
 */
@Route(path = HttpNavigation.MODULE_NAME + "Daemon")
class DaemonActivity : BaseActivity<BasePresenter<BaseModel,IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun getResourceId(): Int = R.layout.activity_daemon

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    fun openDaemon(v : View){
        val thread = Thread(Runnable {
            while (true){
                Thread.sleep(1000)
                println("正在后台跑的线程")
            }
        })
        thread.isDaemon = true
        thread.start()

        finish()

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}
