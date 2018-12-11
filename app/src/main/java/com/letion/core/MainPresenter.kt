package com.letion.core

import com.letion.core.pojo.BookBean
import com.weyee.sdk.api.RxHttpUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import com.weyee.sdk.toast.ToastUtils

/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/11 0011
 */
class MainPresenter(val view: com.letion.core.MainView) {
    fun getBook() {
        RxHttpUtils.createApi(ApiService::class.java).book.compose(Transformer.switchSchedulers(view.dialog()))
            .subscribe(object : ProgressSubscriber<BookBean>() {

                override fun setTag(): String {
                    return "book"
                }

                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: BookBean?) {
                    ToastUtils.show(t?.summary)
                }

            })
    }

    fun getBook(b: Boolean) {
        RxHttpUtils.createApi(ApiService::class.java).bookString.compose(Transformer.switchSchedulers(view.dialog()))
            .subscribe(object : ProgressSubscriber<String>() {

                override fun setTag(): String {
                    return "book-string"
                }

                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: String?) {
                    ToastUtils.show(t)
                }

            })
    }

    fun cancelBook() {
        RxHttpUtils.cancel("book")
    }

    fun cancelBook(b: Boolean) {
        RxHttpUtils.cancel("book-string")
    }
}