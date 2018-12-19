package com.letion.app

import android.app.Dialog
import android.content.Context
import com.letion.app.pojo.BookBean
import com.letion.app.pojo.Top250Bean
import com.weyee.poscore.mvp.IView
import com.weyee.poscore.mvp.IModel
import io.reactivex.Observable
import retrofit2.http.*


/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/19 0019
 */
interface MainContract {
    interface MainView : IView {
        fun dialog(): Dialog?

        fun context(): Context

        fun showProgress(progress: Int)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getBook(@FieldMap maps: Map<String, Any>): Observable<BookBean>

        fun getTop250(@Query("count") count: Int): Observable<Top250Bean>

        fun getBookString(): Observable<String>
    }

}