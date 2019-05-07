package com.wuqi.a_service

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.base.BaseLazyFragment
import com.weyee.poscore.di.component.AppComponent
import com.weyee.sdk.router.Path
import com.wuqi.a_service.di.DaggerTabLayoutComponent
import com.wuqi.a_service.di.WanModule
import com.wuqi.a_service.wan.ProjectBean
import com.wuqi.a_service.wan.WanContract
import com.wuqi.a_service.wan.WanPresenter
import kotlinx.android.synthetic.main.activity_tablayout.*

@Route(path = Path.Service + "TabLayout")
class TabLayoutActivity : BaseActivity<WanPresenter>(), WanContract.TabLayoutView {
    private val fragments = ArrayList<BaseLazyFragment<*>>()
    private val titles = ArrayList<String>()

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerTabLayoutComponent
            .builder()
            .appComponent(appComponent)
            .wanModule(WanModule(this))
            .build()
            .inject(this@TabLayoutActivity)
    }

    override fun getResourceId(): Int = R.layout.activity_tablayout

    override fun initView(savedInstanceState: Bundle?) {
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.projects()
    }

    override fun setProjects(list: List<ProjectBean>?) {
        list?.forEach {
            titles.add(it.name)
            fragments.add(TabLayoutFragment.newInstance(it.id))
            viewPager.adapter?.notifyDataSetChanged()
        }
    }

    override fun useProgressAble(): Boolean {
        return !super.useProgressAble()
    }
}
