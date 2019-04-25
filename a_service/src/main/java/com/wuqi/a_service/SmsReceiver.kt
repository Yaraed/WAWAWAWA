package com.wuqi.a_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.weyee.sdk.toast.ToastUtils


/**
 *  小米8.0测试无效，目前还未找到原因
 * @author wuqi by 2019/4/19.
 */
class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            // 取消广播（这行代码将会让系统收不到短信）
            abortBroadcast()
            val sb = StringBuilder()
            // 接收由SMS传过来的数据
            val bundle = intent?.extras
            // 判断是否有数据
            if (bundle != null) {
                // 通过pdus可以获得接收到的所有短信消息
                val pdus = bundle.get("pdus") as Array<*>
                // 构建短信对象array,并依据收到的对象长度来创建array的大小
                val messages = arrayOfNulls<SmsMessage>(pdus.size)
                for (i in pdus.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }
                // 将送来的短信合并自定义信息于StringBuilder当中
                for (message in messages) {
                    sb.append("短信来源:")
                    // 获得接收短信的电话号码
                    sb.append(message?.displayOriginatingAddress)
                    sb.append("\n------短信内容------\n")
                    // 获得短信的内容
                    sb.append(message?.displayMessageBody)
                }
            }
            ToastUtils.show(sb.toString())
        }
    }
}