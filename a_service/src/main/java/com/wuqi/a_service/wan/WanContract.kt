package com.wuqi.a_service.wan

import com.weyee.poscore.mvp.IModel
import com.weyee.poscore.mvp.IView
import io.reactivex.Observable

/**
 *
 * @author wuqi by 2019/4/17.
 */
interface WanContract {
    interface WanBaseView : IView

    interface WanView : WanBaseView {
        fun setArticle(bean: ArticleBean?)
        fun setBanner(bean: List<BannerBean>?)

        fun onCompleted()
    }

    interface TabLayoutView : WanBaseView {
        fun setProjects(list: List<ProjectBean>?)
    }

    interface TabFragmentView : WanBaseView {
        fun setArticles(list: List<ProjectArticleBeanData>?)
        fun onCompleted()
    }

    interface Model : IModel {
        fun articles(page: Int): Observable<ArticleBean>

        fun banners(): Observable<List<BannerBean>>

        fun projects(): Observable<List<ProjectBean>>

        fun projectArticles(page: Int, cid: Int): Observable<ProjectArticleBean>
    }
}