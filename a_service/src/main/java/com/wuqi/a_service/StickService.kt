package com.wuqi.a_service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 *
 * @author wuqi by 2019/4/16.
 */
class StickService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Service 运行中...")
        return super.onStartCommand(intent, START_FLAG_RETRY, startId)
    }
}