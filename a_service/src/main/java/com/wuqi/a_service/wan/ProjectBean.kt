package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/5/7.
 */
data class ProjectBean(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)