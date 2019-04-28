package com.letion.app

import android.os.Handler
import android.os.Looper
import com.weyee.sdk.toast.ToastUtils

/**
 *
 * @author wuqi by 2019/4/11.
 */
class RefelctTask : ReflectTask2<Void, Int, String> {
    constructor() : super()
    constructor(handler: Handler?) : super(handler)
    constructor(callbackLooper: Looper?) : super(callbackLooper)


    override fun doInBackground(vararg params: Void?): String {
        return "调用成功"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        ToastUtils.show(result)
    }
}