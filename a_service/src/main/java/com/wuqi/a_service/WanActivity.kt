package com.wuqi.a_service

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.hutool.core.util.RandomUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.weyee.poscore.base.App
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.sdk.imageloader.ImageLoader
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.multitype.BaseAdapter
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.multitype.OnRecyclerViewItemClickListener
import com.weyee.sdk.permission.MediaIntents
import com.weyee.sdk.router.MainNavigation
import com.weyee.sdk.router.Path
import com.wuqi.a_service.di.DaggerWanComponent
import com.wuqi.a_service.di.WanModule
import com.wuqi.a_service.wan.*
import kotlinx.android.synthetic.main.activity_wan_android.*

@Route(path = Path.Service + "Wan")
class WanActivity : BaseActivity<WanPresenter>(), WanContract.WanView {
    private lateinit var imageLoader: ImageLoader
    private var adapter: BaseAdapter<Any>? = null
    private var pageIndex = 1

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerWanComponent
            .builder()
            .appComponent(appComponent)
            .wanModule(WanModule(this))
            .build()
            .inject(this@WanActivity)
    }

    override fun getResourceId(): Int = R.layout.activity_wan_android

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarAlpha(this@WanActivity, 112, true)
        BarUtils.setStatusBarVisibility(this@WanActivity, true)

        refreshView.setOnRefreshListener {
            pageIndex = 1
            mPresenter.articles(pageIndex)
        }
        refreshView.setOnLoadMoreListener {
            pageIndex++
            mPresenter.articles(pageIndex)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(context).color(Color.parseColor("#f1f1f1")).size(
                10
            ).build()
        )


        adapter = object : BaseAdapter<Any>(null,
            OnRecyclerViewItemClickListener<Any> { _, _, data, _ ->
                if (data is Data) {
                    startActivity(MediaIntents.newOpenWebBrowserIntent(data.link))
                    //WorkerNavigation(context).toDetailActivity(data.link)
                }
            }) {
            override fun getHolder(v: View, viewType: Int): BaseHolder<Any> {
                if (viewType == 1) {
                    return object : BaseHolder<Any>(v) {
                        override fun setData(data: Any, position: Int) {
                            if (data !is List<*>) {
                                return
                            }
                            getView<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
                            getView<RecyclerView>(R.id.recyclerView).layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            getView<RecyclerView>(R.id.recyclerView).adapter =
                                object : BaseAdapter<BannerBean>(data as List<BannerBean>,
                                    OnRecyclerViewItemClickListener<BannerBean> { _, _, _, position ->
                                        MainNavigation(this@WanActivity).toPhotoViewActivity(
                                            position,
                                            data.map { it.imagePath }.toTypedArray()
                                        )
                                    }) {
                                    override fun getHolder(v: View, viewType: Int): BaseHolder<BannerBean> {
                                        return object : BaseHolder<BannerBean>(v) {
                                            override fun setData(image: BannerBean, p: Int) {
                                                imageLoader.loadImage(
                                                    context,
                                                    GlideImageConfig.builder().resource(
                                                        image.imagePath
                                                    ).imageView(getView(R.id.imageView)).build()
                                                )
                                            }
                                        }


                                    }

                                    override fun getLayoutId(viewType: Int): Int = R.layout.item_banner_image
                                }

                        }
                    }
                } else {

                    return object : BaseHolder<Any>(v) {
                        override fun setData(data: Any, position: Int) {
                            if (data !is Data) {
                                return
                            }
                            imageLoader.loadImage(
                                context,
                                GlideImageConfig.builder().resource(
                                    RandomUtil.randomEle(
                                        listOf(
                                            R.drawable.ic_color_lens_black_24dp,
                                            R.drawable.ic_sentiment_satisfied_black_24dp,
                                            R.drawable.ic_apps_black_24dp,
                                            "https://ws1.sinaimg.cn/large/0065oQSqly1g04lsmmadlj31221vowz7.jpg"
                                        )
                                    )
                                )
                                    .isCircle(true).imageView(getView(R.id.ivIcon)).build()
                            )
                            getView<TextView>(R.id.tvTitle).text = data.author
                            getView<TextView>(R.id.tvTime).text = data.niceDate
                            getView<TextView>(R.id.tvContent).text = data.title
                            getView<TextView>(R.id.tvCategory).text =
                                if (!data.superChapterName.isEmpty()) data.superChapterName else data.chapterName
                        }

                    }
                }
            }

            override fun getLayoutId(viewType: Int): Int =
                if (viewType == 1) R.layout.item_banner else R.layout.item_article

            override fun getItemViewType(position: Int): Int {
                return if (position == 0 && getItem(position) is List<*>) 1 else super.getItemViewType(position)
            }
        }
        recyclerView.adapter = adapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        imageLoader = (context.applicationContext as App).appComponent.imageLoader()
    }

    override fun useProgressAble(): Boolean {
        return !super.useProgressAble()
    }

    override fun setArticle(bean: ArticleBean?) {
        adapter?.addAll(bean?.datas, pageIndex <= 1)
    }

    override fun setBanner(bean: List<BannerBean>?) {
        adapter?.add(0, bean)
    }

    override fun onCompleted() {
        if (pageIndex <= 1) refreshView.finishRefresh() else refreshView.finishLoadMore()
    }

    override fun hasToolbar(): Boolean {
        return !super.hasToolbar()
    }
}
