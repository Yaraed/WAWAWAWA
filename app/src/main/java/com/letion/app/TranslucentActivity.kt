package com.letion.app

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.letion.app.di.module.MainModule
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.sdk.router.MainNavigation
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = MainNavigation.MODULE_NAME + "Translucent")
class TranslucentActivity : BaseActivity<TranslucentPresenter>() {
    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     *
     * @param appComponent
     */
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    /**
     * 如果initView返回0,框架则不会调用[android.app.Activity.setContentView]
     *
     * @return
     */
    override fun getResourceId(): Int = R.layout.activity_translucent

    override fun initView(savedInstanceState: Bundle?) {
        tvContent.setOnClickListener {
            startActivity(
                Intent(
                    this@TranslucentActivity,
                    TranslucentActivity::class.java
                )
            )
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}
