package com.wuqi.a_service.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.weyee.sdk.toast.ToastUtils

/**
 *
 * @author wuqi by 2019/4/23.
 */
class HomeKeyEventBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
            val reason = intent.getStringExtra("reason")

            when (reason) {
                "homekey" -> {
                    ToastUtils.show("点击Home键")
                }
                "recentapps" -> {
                    ToastUtils.show("长按Home键")
                }
            }
        }
    }

    fun register(context: Context) {
        context.registerReceiver(this, IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
    }
}