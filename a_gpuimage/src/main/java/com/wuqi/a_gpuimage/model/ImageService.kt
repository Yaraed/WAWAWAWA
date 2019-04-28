package com.wuqi.a_gpuimage.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author wuqi by 2019/4/15.
 */
interface ImageService {


    //http://gank.io/api/data/福利/10/1
    @GET("api/data/福利/{pageSize}/{pageIndex}")
    fun getImages(@Path("pageSize") pageSize: Int, @Path("pageIndex") pageIndex: Int): Observable<ImageRep>

    companion object {
        const val baseUrl: String = "http://gank.io/"
    }
}