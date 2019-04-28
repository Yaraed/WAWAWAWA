package com.wuqi.a_jetpack.ui.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JetpackViewModel : ViewModel() {
    private val _data: MutableLiveData<String> = MutableLiveData()
    val data: MutableLiveData<String>
        get() = _data

    init {
        _data.value = "Hello Jetpack!"
    }
}
