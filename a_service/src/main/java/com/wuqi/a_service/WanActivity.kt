package com.wuqi.a_service

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.hutool.core.util.RandomUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.weyee.poscore.base.App
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.possupport.repeatclick.OnFastClickListener
import com.weyee.sdk.imageloader.ImageLoader
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.multitype.*
import com.weyee.sdk.permission.MediaIntents
import com.weyee.sdk.router.MainNavigation
import com.weyee.sdk.router.Path
import com.wuqi.a_service.di.DaggerWanComponent
import com.wuqi.a_service.di.WanModule
import com.wuqi.a_service.wan.*
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.activity_wan_android.*

@Route(path = Path.Service + "Wan")
class WanActivity : BaseActivity<WanPresenter>(), WanContract.WanView {
    private lateinit var imageLoader: ImageLoader
    private var adapter: BaseAdapter<Any>? = null
    private var pageIndex = 0
    private var showGridLayout = false

    private companion object {
        val SIZE = 2
    }

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

        header.isShowMenuLeftBackView(true)
        header.setTitle("玩Android")
        header.setOnClickLeftMenuBackListener(object : OnFastClickListener() {
            override fun onFastClick(v: View?) {
                finish()
            }
        })

        header.isShowMenuRightOneView(true)
        header.isShowMenuRightTwoView(true)
        header.setMenuRightOneIcon(R.drawable.ic_more_vert_black_24dp)
        header.setMenuRightTwoIcon(R.drawable.ic_dehaze_black_24dp)
        header.setOnClickRightMenuTwoListener {
            showGridLayout = !showGridLayout
            header.setMenuRightTwoIcon(if (!showGridLayout) R.drawable.ic_dehaze_black_24dp else R.drawable.ic_apps_black_24dp)
            (recyclerView.layoutManager as GridLayoutManager).spanCount = if (showGridLayout) SIZE else 1
            (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
                if (showGridLayout) object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == 0) SIZE else 1
                    }

                }
                else GridLayoutManager.DefaultSpanSizeLookup()
            recyclerView.adapter?.notifyDataSetChanged()
        }

        refreshView.setOnRefreshListener {
            pageIndex = 0
            mPresenter.articles(pageIndex)
        }
        refreshView.setOnLoadMoreListener {
            pageIndex++
            mPresenter.articles(pageIndex)
        }
        val layoutManager = GridLayoutManager(context, 1).apply {
            spanSizeLookup = GridLayoutManager.DefaultSpanSizeLookup()
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 500
            removeDuration = 500
            moveDuration = 500
            changeDuration = 500
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(context).color(Color.parseColor("#f1f1f1")).size(
                10
            ).build()
        )
        recyclerView.addItemDecoration(
            VerticalDividerItemDecoration.Builder(context)
                .visibilityProvider(object : FlexibleDividerDecoration.VisibilityProvider {
                    override fun shouldHideDivider(position: Int, parent: RecyclerView?): Boolean {
                        if (!showGridLayout) {
                            return true
                        } else if (position % SIZE == 0) {
                            return true
                        }
                        return false
                    }

                })
                .color(Color.parseColor("#f1f1f1")).size(
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
                            val url: Any = RandomUtil.randomEle(
                                listOf(
                                    R.drawable.ic_color_lens_black_24dp,
                                    R.drawable.ic_sentiment_satisfied_black_24dp,
                                    R.drawable.ic_apps_black_24dp,
                                    "https://ws1.sinaimg.cn/large/0065oQSqly1g04lsmmadlj31221vowz7.jpg"
                                )
                            )
                            imageLoader.loadImage(
                                context,
                                GlideImageConfig.builder().resource(
                                    url
                                )
                                    .isCircle(true).imageView(getView(R.id.ivIcon)).build()
                            )
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getView<ImageView>(R.id.ivIcon).imageTintList = if (url is Int) ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        context,
                                        RandomUtil.randomEle(
                                            listOf(
                                                android.R.color.holo_blue_bright,
                                                android.R.color.holo_green_light,
                                                android.R.color.holo_red_light,
                                                android.R.color.holo_orange_light
                                            )
                                        )
                                    )
                                ) else null
                            }
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
        refreshView.autoRefresh()
    }

    override fun initData(savedInstanceState: Bundle?) {
        imageLoader = (context.applicationContext as App).appComponent.imageLoader()
    }

    override fun useProgressAble(): Boolean {
        return !super.useProgressAble()
    }

    override fun setArticle(bean: ArticleBean?) {
        adapter?.addAll(bean?.datas, pageIndex <= 0)
    }

    override fun setBanner(bean: List<BannerBean>?) {
        adapter?.add(0, bean)
    }

    override fun onCompleted() {
        if (pageIndex <= 0) refreshView.finishRefresh() else refreshView.finishLoadMore()
    }

    override fun hasToolbar(): Boolean {
        return !super.hasToolbar()
    }
}
