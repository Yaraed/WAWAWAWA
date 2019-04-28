package com.wuqi.a_gpuimage

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.weyee.poscore.base.App
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poswidget.stateview.state.ContentState
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.imageloader.progress.OnRequestListener
import com.weyee.sdk.multitype.BaseAdapter
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.OnRecyclerViewItemClickListener
import com.weyee.sdk.router.MainNavigation
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import com.wuqi.a_gpuimage.di.DaggerImageComponent
import com.wuqi.a_gpuimage.di.ImageContract
import com.wuqi.a_gpuimage.di.ImageMoudle
import com.wuqi.a_gpuimage.di.ImagePresenter
import com.wuqi.a_gpuimage.state.LoadingState
import kotlinx.android.synthetic.main.activity_image.*

@Route(path = Path.GPU + "Image")
class ImageActivity : BaseActivity<ImagePresenter>(), ImageContract.ImageView {

    private var list: MutableList<String>? = null
    private var pageIndex: Int = 1

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerImageComponent
            .builder()
            .appComponent(appComponent)
            .imageMoudle(ImageMoudle(this))
            .build()
            .inject(this@ImageActivity)
    }

    override fun getResourceId(): Int = R.layout.activity_image

    override fun initView(savedInstanceState: Bundle?) {
        stateLayout.stateManager.addState(LoadingState())
        stateLayout.showState(LoadingState.STATE)

        listView.layoutManager = GridLayoutManager(context, 2)
        listView.adapter = object : BaseAdapter<String>(list,
            OnRecyclerViewItemClickListener<String> { view, viewType, data, position ->
                MainNavigation(this@ImageActivity).toPhotoViewActivity(
                    position,
                    (listView.adapter as BaseAdapter<String>).all.toTypedArray()
                )
            }) {
            override fun getLayoutId(viewType: Int): Int = R.layout.item_image

            override fun getHolder(v: View, viewType: Int): BaseHolder<String> {
                return object : BaseHolder<String>(v) {
                    override fun setData(data: String, position: Int) {
                        (context.applicationContext as App).appComponent.imageLoader().loadImage(
                            context,
                            GlideImageConfig.builder().resource(data)
                                .thumbnail(.1f)
                                .listener(object : OnRequestListener<BitmapDrawable> {
                                    override fun onLoadFailed() : Boolean {
                                        ToastUtils.show("图片加载失败")
                                        return true
                                    }

                                    override fun onResourceReady(resource: BitmapDrawable?): Boolean {
                                        val p = Palette.generate(resource?.bitmap).lightVibrantSwatch

                                        return if (p == null) false else {
                                            getView<ImageView>(R.id.imageView).setBackgroundColor(p.rgb)
                                            return true
                                        }
                                    }
                                })
                                .imageView(getView(R.id.imageView)).build()
                        )
                    }

                }
            }

        }

        listView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                pageIndex++
                loadImage()
            }

            override fun onRefresh() {
                pageIndex = 1
                (listView.adapter as BaseAdapter<String>).clear()
                loadImage()
            }

        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadImage()
    }

    override fun useProgressAble(): Boolean {
        return !super.useProgressAble()
    }

    private fun loadImage() {
        mPresenter.getImages(30, pageIndex)
    }

    override fun setImages(bean: List<String>?) {
        (listView.adapter as BaseAdapter<String>).addAll(bean)
    }

    override fun onCompleted() {
        listView.refreshComplete()
        listView.loadMoreComplete()
    }


    override fun showLoading() {
        stateLayout.showState { LoadingState.STATE }
    }

    override fun hideLoading() {
        stateLayout.showState { ContentState.STATE }
    }

}
