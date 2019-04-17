package com.wuqi.a_service.wan

import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/17.
 */
@ActivityScope
class WanPresenter @Inject constructor(model: WanModel?, rootView: WanContract.WanView?) :
    BasePresenter<WanContract.Model, WanContract.WanView>(model, rootView) {

    fun articles(page: Int) {
        if (page <= 1) {
            Observable.merge(mModel.articles(1), mModel.banners())
                .compose(Transformer.switchSchedulers(progressAble))
                .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
                .subscribe(object : ProgressSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        if (t is ArticleBean) {
                            mView.setArticle(t)
                        } else if (t is List<*>) {
                            mView.setBanner(t as List<BannerBean>)
                        }
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        mView.onCompleted()
                    }

                })
        } else {
            mModel.articles(page)
                .compose(Transformer.switchSchedulers(progressAble))
                .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
                .subscribe(object : ProgressSubscriber<ArticleBean>() {
                    override fun onSuccess(t: ArticleBean?) {
                        mView.setArticle(t)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        mView.onCompleted()
                    }

                })
        }

    }
}