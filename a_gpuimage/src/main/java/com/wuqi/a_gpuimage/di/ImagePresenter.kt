package com.wuqi.a_gpuimage.di

import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/12.
 */
@ActivityScope
class ImagePresenter @Inject constructor(model: ImageModel?, rootView: ImageContract.ImageView?) :
    BasePresenter<ImageContract.Model, ImageContract.ImageView>(model, rootView) {

    fun getImages(pageSize: Int, pageIndex: Int) {
        mModel.getImages(pageSize, pageIndex).compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<List<String>>() {
                override fun onSuccess(t: List<String>?) {
                    mView.setImages(t)
                }

            })
    }
}