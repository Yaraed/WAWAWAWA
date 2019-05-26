package com.wuqi.a_service.services

import com.wuqi.a_service.wan.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author wuqi by 2019/4/17.
 */
interface LotteryService {

    companion object {
        const val baseUrl: String = "http://apis.juhe.cn/"

        const val ApiKey : String = "612601042b79f5c3bc3692f35c362376"
    }


    // 种类
    @GET("lottery/types?key=$ApiKey")
    fun lotterys(): Observable<LotteryResponse<List<LotteryCategory>>>

    /**
     * 当期开奖结果查询
     * @param lottery_id 种类ID
     * @param lottery_no 期数
     */
    @GET("lottery/query?key=$ApiKey")
    fun infos(@Query(value = "lottery_id") lottery_id: String, @Query(value = "lottery_no") lottery_no: String?): Observable<LotteryResponse<LotteryInfo>>

    /**
     * 历史开奖结果列表
     */
    @GET("lottery/history?key=$ApiKey")
    fun historys(@Query(value = "lottery_id") lottery_id: String, @Query(value = "page") page: Int, @Query(value = "page_size") page_size: Int): Observable<LotteryResponse<LotteryHistory>>

    /**
     * 是否中
     * @param lottery_res 购买的号码，号码之间用英文逗号隔开，红色球和蓝色求之间用@连接，例如：01,11,02,09,14,22,25@05,03
     */
    @GET("lottery/bonus?key=$ApiKey")
    fun bonus(
        @Query(value = "lottery_id") lottery_id: String, @Query(value = "lottery_no") lottery_no: String, @Query(
            value = "lottery_res"
        ) lottery_res: String
    ): Observable<LotteryResponse<LotteryBonus>>

}