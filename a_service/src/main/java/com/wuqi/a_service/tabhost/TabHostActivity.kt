package com.wuqi.a_service.tabhost

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TabHost
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import com.wuqi.a_service.R
import kotlinx.android.synthetic.main.activity_tab_host.*


@Route(path = Path.Service + "TabHost")
class TabHostActivity : BaseActivity<BasePresenter<BaseModel, IView>>(), TabHost.OnTabChangeListener {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_tab_host

    override fun initView(savedInstanceState: Bundle?) {
        tabhost.setup(this, supportFragmentManager, R.id.frameLayout)
        Tab.values().forEach {
            val table = tabhost.newTabSpec(getString(it.resName))
            val view = View.inflate(this, R.layout.item_table_view, null)
            val title = view.findViewById<TextView>(R.id.tv_tab_title)
            val avatar = view.findViewById<ImageView>(R.id.iv_tab_icon)
            avatar.setImageDrawable(resources.getDrawable(it.resIcon))
            title.text = getString(it.resName)
            table.setIndicator(view)
            table.setContent { View(context) }
            val bundle = Bundle()
            bundle.putInt("position", it.idx)
            tabhost.addTab(table, it.clz, bundle)
        }

        tabhost.tabWidget.dividerDrawable = null
        tabhost.currentTab = 0 // 默认选中第一个(首页)
        tabhost.setOnTabChangedListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onTabChanged(tabId: String?) {
        val size = tabhost.tabWidget.tabCount

        for (i in 0 downTo size) {
            if (i == tabhost.currentTab) {
                tabhost.currentTabView?.isSelected = true
            } else {
                tabhost.tabWidget.getChildAt(i)?.isSelected = false
            }
        }
    }
}
