package com.wuqi.a_jetpack.viewmodel.jetpack

import androidx.lifecycle.ViewModel
import com.wuqi.a_jetpack.data.jetpack.PlaceRepository

/**
 *
 * @author wuqi by 2019/4/3.
 */
class PlaceViewModel(val repository : PlaceRepository) : ViewModel() {
    val provinceId : Int = 0
    val cityId : Int = 0
    val areaId : Int = 0

    val list : MutableList<String> = mutableListOf()

    fun getProvince() {
        list.clear()
        //repository.getProvinceList()
    }

}