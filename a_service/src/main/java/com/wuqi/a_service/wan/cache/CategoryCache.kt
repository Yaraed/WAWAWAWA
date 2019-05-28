package com.wuqi.a_service.wan.cache

import com.wuqi.a_service.wan.LotteryCategory

/**
 *
 * @author wuqi by 2019/5/28.
 */
data class CategoryCache(
    val error_code: Int,
    val reason: String,
    val result: List<LotteryCategory>
)