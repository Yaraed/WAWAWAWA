package com.wuqi.a_service.wan

import com.weyee.poscore.di.scope.FragmentScope
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/17.
 */
@FragmentScope
class TabLayoutPresenter @Inject constructor(model: TabModel?, rootView: WanContract.WanBaseView?) :
    BasePresenter<WanContract.Model, WanContract.WanBaseView>(model, rootView) {

    fun articles(page: Int, cid: Int) {
        mModel.projectArticles(page, cid).compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<ProjectArticleBean>() {
                override fun onSuccess(t: ProjectArticleBean?) {
                    if (mView is WanContract.TabFragmentView) {
                        (mView as WanContract.TabFragmentView).setArticles(t?.datas)
                    }
                }

                override fun onCompleted() {
                    super.onCompleted()
                    if (mView is WanContract.TabFragmentView) {
                        (mView as WanContract.TabFragmentView).onCompleted()
                    }
                }

            })
    }
}