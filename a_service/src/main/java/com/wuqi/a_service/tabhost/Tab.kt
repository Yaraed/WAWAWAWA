package com.wuqi.a_service.tabhost

import com.wuqi.a_service.R

/**
 * @author wuqi by 2019/5/20.
 */
enum class Tab private constructor(var idx: Int, var resName: Int, var resIcon: Int, var clz: Class<*>) {

    HOME(1, R.string.service_table_home, R.drawable.ic_home_black_24dp, TabHostFragment::class.java),
    SYSTEM(2, R.string.service_table_system, R.drawable.ic_system_black_24dp, TabHostFragment2::class.java),
    Navigation(3, R.string.service_table_navigation, R.drawable.ic_navigation_black_24dp, TabHostFragment3::class.java),
    PROJECT(4, R.string.service_table_project, R.drawable.ic_project_black_24dp, TabHostFragment4::class.java),
    ME(5, R.string.service_table_me, R.drawable.ic_settings_black_24dp, TabHostFragment5::class.java)
}
