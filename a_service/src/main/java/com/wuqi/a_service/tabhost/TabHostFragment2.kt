package com.wuqi.a_service.tabhost

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.weyee.poscore.base.BaseLazyFragment
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.wuqi.a_service.R
import kotlinx.android.synthetic.main.fragment_tab_host.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 *
 */
class TabHostFragment2 : BaseLazyFragment<BasePresenter<BaseModel, IView>>() {

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.fragment_tab_host

    override fun setData(data: Any?) {
    }

    override fun lazyLoadData() {
        textView.text = String.format(getString(R.string.hello_blank_fragment), position)
    }

}
