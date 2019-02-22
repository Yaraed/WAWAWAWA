package com.letion.app

import androidx.lifecycle.LifecycleOwner
import com.letion.app.pojo.BookBean
import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.sdk.api.RxHttpUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import com.weyee.sdk.api.observer.transmission.DownloadTransformer
import com.weyee.sdk.api.observer.transmission.UploadTransformer
import com.weyee.sdk.toast.ToastUtils
import javax.inject.Inject

/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/11 0011
 */
@ActivityScope
class MainPresenter @Inject constructor(view: MainContract.MainView,model: MainModel) : BasePresenter<MainModel, MainContract.MainView>(model,view) {

    fun getBook() {
        val map: Map<String, Any> = mapOf("page" to 1, "name" to "刘枫")
        mModel.getBook(map).compose(Transformer.switchSchedulers(mView.dialog()))
            .`as`(RxLiftUtils.bindLifecycle(mView as LifecycleOwner))
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
        mModel.getBookString().compose(Transformer.switchSchedulers(mView.dialog()))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
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

    fun downloadApk() {
        val url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk"
        val fileName = "alipay.apk"
        RxHttpUtils.createApi(ApiService::class.java).downloadFile(url)
            .compose(DownloadTransformer.transformerFormParams(
                mView.context().getExternalFilesDir(null),
                fileName
            ) { _, _, progress, _, _ ->
                run {
                    mView.showProgress(progress)
                }
            })
            .compose(Transformer.switchSchedulers(mView.dialog()))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<String>() {
                override fun setTag(): String {
                    return "download-apk"
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

    fun uploadImages(list: List<String>) {
        val url = "http://t.xinhuo.com/index.php/Api/Pic/uploadPic"
        RxHttpUtils.createApi(ApiService::class.java)
            .uploadFiles(url, UploadTransformer.transformerFormParams(null, list))
            .compose(Transformer.switchSchedulers(mView.dialog()))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<String>() {
                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: String?) {
                    ToastUtils.show("上传完毕: $t")
                }

            })
    }

    fun cancelBook() {
        RxHttpUtils.cancel("book")
    }

    fun cancelBook(b: Boolean) {
        RxHttpUtils.cancel("book-string")
    }

    fun cancelApk() {
        RxHttpUtils.cancel("download-apk")
    }
}