package com.wuqi.a_jetpack.ui.jetpack

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.weyee.poscore.base.BaseFragment
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.wuqi.a_jetpack.R

class JetpackFragment : BaseFragment<BasePresenter<BaseModel, IView>>() {
    companion object {
        fun newInstance() = JetpackFragment()
    }

    private lateinit var viewModel: JetpackViewModel

    override fun setupFragmentComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.jetpack_fragment

    override fun initView(savedanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(JetpackViewModel::class.java)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun setData(data: Any?) {
    }

}
