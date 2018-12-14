package com.letion.app

import android.content.Intent
import android.os.Bundle
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import kotlinx.android.synthetic.main.activity_main.*

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
