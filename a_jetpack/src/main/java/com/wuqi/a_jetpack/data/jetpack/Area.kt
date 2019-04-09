package com.wuqi.a_jetpack.data.jetpack

import com.google.gson.annotations.SerializedName

/**
 *
 * @author wuqi by 2019/4/3.
 */
data class Area(@SerializedName("id") val code: Int, @SerializedName("name") val name: String) {
    var provinceId = 0
    var cityId = 0
}