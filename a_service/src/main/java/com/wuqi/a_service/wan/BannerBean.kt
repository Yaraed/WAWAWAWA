package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/4/17.
 */
data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)