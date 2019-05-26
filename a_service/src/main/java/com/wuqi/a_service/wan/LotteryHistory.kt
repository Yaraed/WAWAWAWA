package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/5/20.
 */
data class LotteryHistory(
    val lotteryResList: List<LotteryRes>,
    val page: Int,
    val pageSize: Int,
    val totalPage: Int
)

data class LotteryRes(
    val lottery_date: String,
    val lottery_exdate: String,
    val lottery_id: String,
    val lottery_no: String,
    val lottery_pool_amount: String,
    val lottery_res: String,
    val lottery_sale_amount: String
)