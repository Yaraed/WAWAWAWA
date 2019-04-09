package com.wuqi.a_jetpack.data.jetpack

import com.weyee.sdk.api.gson.GsonAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 *
 * @author wuqi by 2019/4/3.
 */
object RetrofitCreator {
    private const val HOST = "http://guolin.tech/"

    private val okHttpClient = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl(HOST)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()))
        .addConverterFactory(ScalarsConverterFactory.create()).build()

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)

}