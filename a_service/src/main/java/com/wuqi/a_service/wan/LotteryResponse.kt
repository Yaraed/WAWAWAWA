package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/5/20.
 */
data class LotteryResponse<T>(
    val error_code: Int,
    val reason: String,
    val result: T
)
