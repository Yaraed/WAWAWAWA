package com.letion.app.photo

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.letion.app.R
import com.letion.app.photo.photoview.PhotoView
import com.letion.app.photo.photoview.PhotoViewAttacher
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import kotlinx.android.synthetic.main.activity_photo.*
import javax.sql.DataSource


/**
 * 图片查看器
 */
@Route(path = Path.MAIN + "PhotoView")
class MImageViewActivity : BaseActivity<BasePresenter<BaseModel, IView>>(), PhotoViewAttacher
.OnPhotoTapListener, ViewPager.OnPageChangeListener {

    private var imgUrls: Array<String>? = null

    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     *
     * @param appComponent
     */
    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    /**
     * 如果initView返回0,框架则不会调用[Activity.setContentView]
     *
     * @return
     */
    override fun getResourceId(): Int {
        return R.layout.activity_photo
    }

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarAlpha(this@MImageViewActivity, 112, true)
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar)


        val args = intent.extras
        var currIndex = 0
        if (args != null) {
            currIndex = intent.getIntExtra("index", 0)
            imgUrls = intent.getStringArrayExtra("urls")
        }
        viewPager.adapter = ViewPagerAdapter()
        viewPager.currentItem = currIndex
        viewPager.setOnPageChangeListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        onPageSelected(viewPager.currentItem)
    }

    override fun canSwipeBack(): Boolean {
        return !super.canSwipeBack()
    }

    override fun hasToolbar(): Boolean {
        return !super.hasToolbar()
    }

    /**
     * A callback to receive where the user taps on a photo. You will only receive a callback if
     * the user taps on the actual photo, tapping on 'whitespace' will be ignored.
     *
     * @param view - View the user tapped.
     * @param x    - where the user tapped from the of the Drawable, as percentage of the
     * Drawable width.
     * @param y    - where the user tapped from the top of the Drawable, as percentage of the
     * Drawable height.
     */
    override fun onPhotoTap(view: View?, x: Float, y: Float) {
        finish()
    }

    /**
     * A simple callback where out of photo happened;
     */
    override fun onOutsidePhotoTap() {
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager.SCROLL_STATE_IDLE
     *
     * @see ViewPager.SCROLL_STATE_DRAGGING
     *
     * @see ViewPager.SCROLL_STATE_SETTLING
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position Position index of the first page currently being displayed.
     * Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @SuppressLint("SetTextI18n")
    override fun onPageSelected(position: Int) {
        tv_indicator.text = (position + 1).toString() + " / " + imgUrls?.size
    }

    /**
     * ViewPager的适配器
     *
     * @author guolin
     */
    internal inner class ViewPagerAdapter : PagerAdapter() {

        private var inflater: LayoutInflater = layoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = inflater.inflate(R.layout.item_photoview, container, false)
            val photoView = view.findViewById(R.id.photoView) as PhotoView
            val spinner = view.findViewById(R.id.loading) as ProgressBar
            // 保存网络图片的路径
            val url = imgUrls?.get(position)

            spinner.visibility = View.VISIBLE
            spinner.isClickable = false
            Glide.with(this@MImageViewActivity).load(url)
                .listener(object : RequestListener<Drawable> {
                    /**
                     * Called when an exception occurs during a load, immediately before
                     * [Target.onLoadFailed]. Will only be called if we currently want to display an
                     * image for the given model in the given target. It is recommended to create a single instance
                     * per activity/fragment rather than instantiate a new object for each call to `Glide.with(fragment/activity).load()` to avoid object churn.
                     *
                     *
                     * It is not safe to reload this or a different model in this callback. If you need to do so
                     * use [com.bumptech.glide.RequestBuilder.error] instead.
                     *
                     *
                     * Although you can't start an entirely new load, it is safe to change what is displayed in the
                     * [Target] at this point, as long as you return `true` from the method to prevent
                     * [Target.onLoadFailed] from being called.
                     *
                     * For example:
                     * <pre>
                     * `public boolean onLoadFailed(Exception e, T model, Target target, boolean isFirstResource) {
                     * target.setPlaceholder(R.drawable.a_specific_error_for_my_exception);
                     * return true; // Prevent onLoadFailed from being called on the Target.
                     * }
                    ` *
                    </pre> *
                     *
                     *
                     * @param e               The maybe `null` exception containing information about why the
                     * request failed.
                     * @param model           The model we were trying to load when the exception occurred.
                     * @param target          The [Target] we were trying to load the image into.
                     * @param isFirstResource `true` if this exception is for the first resource to load.
                     * @return `true` to prevent [Target.onLoadFailed] from being called on
                     * `target`, typically because the listener wants to update the `target` or the object
                     * the `target` wraps itself or `false` to allow [Target.onLoadFailed]
                     * to be called on `target`.
                     */
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        spinner.visibility = View.GONE
                        return false
                    }

                    /**
                     * Called when a load completes successfully, immediately before [ ][Target.onResourceReady].
                     *
                     * @param resource          The resource that was loaded for the target.
                     * @param model             The specific model that was used to load the image.
                     * @param target            The target the model was loaded into.
                     * @param dataSource        The [DataSource] the resource was loaded from.
                     * @param isFirstResource   `true` if this is the first resource to in this load to be
                     * loaded into the target. For example when loading a thumbnail and a
                     * full-sized image, this will be `true` for the first image to
                     * load and `false` for the second.
                     *
                     * @return `true` to prevent [Target.onLoadFailed] from being called on
                     * `target`, typically because the listener wants to update the `target` or the object
                     * the `target` wraps itself or `false` to allow [Target.onLoadFailed]
                     * to be called on `target`.
                     */
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        spinner.visibility = View.GONE

                        /**这里应该是加载成功后图片的高 */
                        val height = photoView.height

                        val wHeight = windowManager.defaultDisplay.height
                        if (height > wHeight) {
                            photoView.scaleType = ImageView.ScaleType.CENTER_CROP
                        } else {
                            photoView.scaleType = ImageView.ScaleType.FIT_CENTER
                        }
                        return false
                    }
                }).into(photoView)

            photoView.setOnPhotoTapListener(this@MImageViewActivity)
            container.addView(view, 0)
            return view
        }

        override fun getCount(): Int {
            return if (imgUrls == null || imgUrls?.size === 0) {
                0
            } else imgUrls?.size!!
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}
