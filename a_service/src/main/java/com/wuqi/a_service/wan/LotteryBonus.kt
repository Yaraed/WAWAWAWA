package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/5/20.
 */
data class LotteryBonus(
    val buy_blue_ball_num: String,
    val buy_red_ball_num: String,
    val hit_blue_ball_num: String,
    val hit_red_ball_num: String,
    val in_money: String,
    val is_prize: String,
    val lottery_date: String,
    val lottery_id: String,
    val lottery_name: String,
    val lottery_no: String,
    val lottery_prize: List<LotteryPrize>,
    val lottery_res: String,
    val prize_msg: String,
    val real_lottery_res: String
)
