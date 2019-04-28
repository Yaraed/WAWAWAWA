package com.wuqi.a_jetpack.data.jetpack

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *
 * @author wuqi by 2019/4/3.
 */
class PlaceRepository {
    private val placeService = RetrofitCreator.create(PlaceService::class.java)

    private suspend fun fetchProvinceList() = placeService.getProvinces().await()
    private suspend fun fetchCityList(provinceId: Int) = placeService.getCitys(provinceId).await()
    private suspend fun fetchAreaList(provinceId: Int, cityId: Int) = placeService.getAreas(provinceId, cityId).await()

    suspend fun getProvinceList(): List<Province> = withContext(Dispatchers.IO) {
        return@withContext fetchProvinceList()
    }

    suspend fun getCityList(provinceId: Int): List<City> = withContext(Dispatchers.IO) {
        val list = fetchCityList(provinceId)
        list.forEach { it.provinceId = provinceId }
        return@withContext list
    }

    suspend fun getAreaList(provinceId: Int, cityId: Int): List<Area> = withContext(Dispatchers.IO) {
        val list = fetchAreaList(provinceId, cityId)
        list.forEach {
            it.provinceId = provinceId
            it.cityId = cityId
        }
        return@withContext list
    }


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }
}