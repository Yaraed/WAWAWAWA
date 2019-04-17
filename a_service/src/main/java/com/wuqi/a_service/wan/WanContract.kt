package com.wuqi.a_service.wan

import com.weyee.poscore.mvp.IModel
import com.weyee.poscore.mvp.IView
import io.reactivex.Observable

/**
 *
 * @author wuqi by 2019/4/17.
 */
interface WanContract {
    interface WanView : IView {
        fun setArticle(bean: ArticleBean?)
        fun setBanner(bean: List<BannerBean>?)

        fun onCompleted()
    }

    interface Model : IModel {
        fun articles(page: Int): Observable<ArticleBean>

        fun banners(): Observable<List<BannerBean>>
    }
}