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

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 *
 * @author wuqi by 2019/3/4.
 */
class BindService : Service() {
    override fun onCreate() {
        super.onCreate()
        println("Service 创建")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("start commend")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Service 销毁")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return aidlService
    }

    inner class DefaultBinder : Binder() {
        fun start() {
            println("可以做一些逻辑处理")
        }
    }

    private val aidlService : IMyAidlInterface.Stub = object : IMyAidlInterface.Stub(){
        override fun plus(x: Int, y: Int): Int {
            return x+y
        }

        override fun start() {
            println("AIDL 生成远程调用")
        }

    }

}