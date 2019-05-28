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
class WanPresenter @Inject constructor(model: WanModel?, rootView: WanContract.WanBaseView?) :
    BasePresenter<WanContract.Model, WanContract.WanBaseView>(model, rootView) {

    fun articles(page: Int) {
        if (page <= 0) {
            Observable.merge(mModel.articles(0), mModel.banners())
                .compose(Transformer.switchSchedulers(progressAble))
                .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
                .subscribe(object : ProgressSubscriber<Any>() {
                    override fun onSuccess(t: Any?) {
                        if (t is ArticleBean) {
                            (mView as WanContract.WanView).setArticle(t)
                        } else if (t is List<*>) {
                            (mView as WanContract.WanView).setBanner(t as List<BannerBean>)
                        }
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        (mView as WanContract.WanView).onCompleted()
                    }

                })
        } else {
            mModel.articles(page)
                .compose(Transformer.switchSchedulers(progressAble))
                .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
                .subscribe(object : ProgressSubscriber<ArticleBean>() {
                    override fun onSuccess(t: ArticleBean?) {
                        (mView as WanContract.WanView).setArticle(t)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        (mView as WanContract.WanView).onCompleted()
                    }

                })
        }

    }

    fun projects() {
        mModel.projects()
            .compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<List<ProjectBean>>() {
                override fun onSuccess(t: List<ProjectBean>?) {
                    if (mView is WanContract.TabLayoutView) {
                        (mView as WanContract.TabLayoutView).setProjects(t)
                    }
                }

            })
    }
}