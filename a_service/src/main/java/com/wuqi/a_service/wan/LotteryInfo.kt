package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/5/20.
 */
data class LotteryInfo(
    val lottery_date: String,
    val lottery_exdate: String,
    val lottery_id: String,
    val lottery_name: String,
    val lottery_no: String,
    val lottery_pool_amount: String,
    val lottery_prize: List<LotteryPrize>,
    val lottery_res: String,
    val lottery_sale_amount: String
)