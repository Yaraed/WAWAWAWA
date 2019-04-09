package com.wuqi.a_jetpack

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import com.wuqi.a_jetpack.ui.jetpack.JetpackFragment

@Route(path = Path.Jetpack + "Jetpack")
class JetpackActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.jetpack_activity

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, JetpackFragment.newInstance())
                .commitNow()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

}
