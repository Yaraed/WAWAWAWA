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
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.koushikdutta.async.http.AsyncHttpClient
import com.koushikdutta.async.http.WebSocket
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.api.rxutil.RxJavaUtils
import com.weyee.sdk.api.rxutil.task.RxUITask
import com.weyee.sdk.router.HttpNavigation
import com.weyee.sdk.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_web_socket.*

@Route(path = HttpNavigation.MODULE_NAME + "WebSocket")
class WebSocketActivity : BaseActivity<BasePresenter<BaseModel,IView>>() {
    private lateinit var list : MutableList<CharSequence>
    private var webSocket: WebSocket? = null

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_web_socket

    override fun initView(savedInstanceState: Bundle?) {
        val layoutParams = scrollView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = -BarUtils.getStatusBarHeight()

        list = mutableListOf()

        listView.adapter = ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,list)
    }

    override fun initData(savedInstanceState: Bundle?) {

        AsyncHttpClient.getDefaultInstance().websocket("ws://121.40.165.18:8800","my-protocol") { ex, webSocket ->
            run {
                this.webSocket = webSocket
                if (ex != null) {
                    return@run
                }
                webSocket.setStringCallback {
                    list.add(Html.fromHtml("Form Server: \n\t$it"))
                    RxJavaUtils.doInUIThread(object : RxUITask<String>(null){
                        override fun doInUIThread(t: String?) {
                            notifyDataSetChanged()
                        }

                    })
                }
                webSocket.setDataCallback { _, bb ->
                    run {
                        list.add(Html.fromHtml("Form Server: \n\tI got some bytes!"))
                        runOnUiThread {  notifyDataSetChanged() }
                        bb.recycle()
                    }
                }
            }
        }
    }

    /**
     * 开启服务
     */
    fun sendMsg(v : View){
        if (TextUtils.isEmpty(editText.text.toString().trim())){
            ToastUtils.show("请输入信息")
            return
        }
        list.add(Html.fromHtml("Wo: \n\t${editText.text}"))
        webSocket?.send(editText.text.toString())
        editText.setText("")
    }

    fun notifyDataSetChanged(){
        (listView.adapter as BaseAdapter).notifyDataSetChanged()
        listView.smoothScrollByOffset(listView.adapter.count)
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket?.close()
    }
}
