package com.letion.app

import android.app.Dialog
import android.content.Context
import com.weyee.poscore.mvp.IView

/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/11 0011
 */
interface MainView : IView {
    fun dialog(): Dialog?

    fun context(): Context

    fun showProgress(progress: Int)
}