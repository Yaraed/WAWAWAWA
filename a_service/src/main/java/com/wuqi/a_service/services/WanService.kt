package com.wuqi.a_service.services

import com.weyee.sdk.api.bean.HttpResponse
import com.wuqi.a_service.wan.ArticleBean
import com.wuqi.a_service.wan.BannerBean
import com.wuqi.a_service.wan.ProjectArticleBean
import com.wuqi.a_service.wan.ProjectBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 * @author wuqi by 2019/4/17.
 */
interface WanService {
    @GET("article/list/{pageIndex}/json")
    fun articles(@Path(value = "pageIndex") page: Int): Observable<HttpResponse<ArticleBean>>

    @GET("banner/json")
    fun banners(): Observable<HttpResponse<List<BannerBean>>>

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    fun projests(): Observable<HttpResponse<List<ProjectBean>>>

    /**
     * 项目类别下的项目列表
     */
    @GET("project/list/{pageIndex}/json")
    fun projestArtiles(@Path(value = "pageIndex") page: Int, @Query("cid") cid: Int): Observable<HttpResponse<ProjectArticleBean>>

}