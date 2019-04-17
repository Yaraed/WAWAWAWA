package com.wuqi.a_service.services

import com.weyee.sdk.api.bean.HttpResponse
import com.wuqi.a_service.wan.ArticleBean
import com.wuqi.a_service.wan.BannerBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author wuqi by 2019/4/17.
 */
interface WanService {
    @GET("article/list/{pageIndex}/json")
    fun articles(@Path(value = "pageIndex") page: Int): Observable<HttpResponse<ArticleBean>>

    @GET("banner/json")
    fun banners(): Observable<HttpResponse<List<BannerBean>>>
}