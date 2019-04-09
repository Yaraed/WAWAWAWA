package com.wuqi.a_jetpack.data.jetpack

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author wuqi by 2019/4/3.
 */
interface PlaceService {
    @GET("api/china")
    fun getProvinces(): Call<MutableList<Province>>


    @GET("api/china/{provinceId}")
    fun getCitys(@Path("provinceId") provinceId: Int): Call<MutableList<City>>

    @GET("api/china/{provinceId}/{cityId}")
    fun getAreas(@Path("provinceId") provinceId: Int, @Path("cityId") cityId: Int): Call<MutableList<Area>>
}