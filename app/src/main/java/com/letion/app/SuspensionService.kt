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

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Debug
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.BaseAdapter
import com.weyee.sdk.multitype.OnRecyclerViewItemClickListener

/**
 * 悬浮窗实现逻辑
 * @author wuqi by 2019/3/8.
 */
@SuppressLint("Registered")
class SuspensionService : Service() {
    private lateinit var windowManger: WindowManager
    private var frameLayout: FrameLayout? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createTocuher()
    }

    override fun onDestroy() {
        if (frameLayout != null) {
            windowManger.removeView(frameLayout)
        }
        super.onDestroy()
    }

    /**
     * 创建悬浮窗
     */
    private fun createTocuher() {
        windowManger = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }

        // 设置背景透明
        params.format = PixelFormat.RGBA_8888
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //设置窗口初始停靠位置.
        params.x = 0
        params.y = 0
        params.gravity = Gravity.TOP or Gravity.RIGHT

        // 设置窗口的大小
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT

        frameLayout = FrameLayout(applicationContext)
        val recyclerView = RecyclerView(applicationContext)
        recyclerView.setBackgroundColor(Color.parseColor("#66000000"))

        frameLayout?.addView(recyclerView)

        windowManger.addView(frameLayout, params)

        //主动计算出当前View的宽高信息.
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        val list = mutableListOf<String>()

        list.add(String.format("%s: %s", "getGlobalAllocCount", Debug.getGlobalAllocCount()))
        list.add(String.format("%s: %s", "getGlobalAllocSize", Debug.getGlobalAllocSize()))
        list.add(String.format("%s: %s", "getGlobalClassInitCount", Debug.getGlobalClassInitCount()))
        list.add(String.format("%s: %s", "getGlobalClassInitTime", Debug.getGlobalClassInitTime()))
        list.add(String.format("%s: %s", "getGlobalExternalAllocCount", Debug.getGlobalExternalAllocCount()))
        list.add(String.format("%s: %s", "getGlobalExternalAllocSize", Debug.getGlobalExternalAllocSize()))
        list.add(String.format("%s: %s", "getGlobalExternalFreedCount", Debug.getGlobalExternalFreedCount()))
        list.add(String.format("%s: %s", "getGlobalExternalFreedSize", Debug.getGlobalExternalFreedSize()))
        list.add(String.format("%s: %s", "getGlobalFreedCount", Debug.getGlobalFreedCount()))
        list.add(String.format("%s: %s", "getGlobalFreedSize", Debug.getGlobalFreedSize()))
        list.add(String.format("%s: %s", "getGlobalGcInvocationCount", Debug.getGlobalGcInvocationCount()))
        list.add("stopSelf")


        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = object :
            BaseAdapter<String>(list, OnRecyclerViewItemClickListener<String> { _: View, _: Int, s: String, _: Int ->
                if ("stopSelf" == s) {
                    stopSelf()
                }
            }) {
            override fun getHolder(v: View, viewType: Int): BaseHolder<String> {
                return object : BaseHolder<String>(v) {
                    override fun setData(data: String, position: Int) {
                        (itemView as TextView).text = data
                    }

                }
            }

            override fun getLayoutId(viewType: Int): Int = android.R.layout.simple_list_item_1

        }

        frameLayout?.setOnClickListener {

        }
    }
}