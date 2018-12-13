package com.letion.app

import android.app.Dialog
import android.content.Context

/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/11 0011
 */
interface MainView {
    fun dialog(): Dialog?

    fun context(): Context

    fun showProgress(progress: Int)
}