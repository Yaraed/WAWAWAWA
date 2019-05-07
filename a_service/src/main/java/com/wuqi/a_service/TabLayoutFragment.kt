package com.wuqi.a_service


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.weyee.poscore.base.BaseLazyFragment
import com.weyee.poscore.di.component.AppComponent
import com.wuqi.a_service.di.DaggerTabFragmentComponent
import com.wuqi.a_service.di.TabModule
import com.wuqi.a_service.wan.ProjectArticleBeanData
import com.wuqi.a_service.wan.TabLayoutPresenter
import com.wuqi.a_service.wan.WanContract
import kotlinx.android.synthetic.main.fragment_tab_layout.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TabLayoutFragment : BaseLazyFragment<TabLayoutPresenter>(), WanContract.TabFragmentView {
    private var cid: Int = -1
    private var page: Int = 1
    private lateinit var list: MutableList<String>

    companion object {
        fun newInstance(cid: Int): TabLayoutFragment {
            val fragment = TabLayoutFragment()
            val bundle = Bundle()
            bundle.putInt("cid", cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        DaggerTabFragmentComponent
            .builder()
            .appComponent(appComponent)
            .tabModule(TabModule(this))
            .build()
            .inject(this@TabLayoutFragment)
    }

    override fun getResourceId(): Int = R.layout.fragment_tab_layout

    override fun initView(savedanceState: Bundle?) {
        super.initView(savedanceState)

        cid = savedanceState?.getInt("cid") ?: (arguments?.getInt("cid") ?: -1)

        list = mutableListOf()
        listView.adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, list)
        refreshView.setOnRefreshListener {
            page = 1
            lazyLoadData()
        }
        refreshView.setOnLoadMoreListener {
            page++
            lazyLoadData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("cid", cid)
    }

    override fun setData(data: Any?) {
    }

    override fun lazyLoadData() {
        mPresenter.articles(page, cid)
    }

    override fun setArticles(list: List<ProjectArticleBeanData>?) {
        if (page <= 1) {
            this.list.clear()
        }
        list?.forEach {
            this.list.add(it.title)
        }
        (listView.adapter as BaseAdapter).notifyDataSetChanged()
    }

    override fun onCompleted() {
        refreshView.finishRefresh()
        refreshView.finishLoadMore()
    }
}
