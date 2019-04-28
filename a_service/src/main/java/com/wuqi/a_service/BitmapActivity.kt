package com.wuqi.a_service

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import cn.hutool.core.util.RandomUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.api.rxutil.RxJavaUtils
import com.weyee.sdk.api.rxutil.task.RxAsyncTask
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import com.wuqi.a_service.services.HomeKeyEventBroadCastReceiver
import com.wuqi.a_service.services.ShakeSensor
import kotlinx.android.synthetic.main.activity_bitmap.*

@Route(path = Path.Service + "Bitmap")
class BitmapActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private var homeKeyEventBroadCastReceiver: HomeKeyEventBroadCastReceiver? = null

    object FLAG {
        val f =
            arrayListOf(Bitmap.Config.ARGB_8888, Bitmap.Config.ARGB_4444, Bitmap.Config.RGB_565, Bitmap.Config.ALPHA_8)
    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_bitmap

    override fun initView(savedInstanceState: Bundle?) {
        val service = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        service.registerListener(object : ShakeSensor(this@BitmapActivity) {
            override fun createLottery() {
                ToastUtils.show("生成一注彩排哦")
            }

        }, service.getDefaultSensor(Sensor.TYPE_ALL), SensorManager.SENSOR_DELAY_NORMAL)
        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    startActivity(Intent(context, SearchActivity::class.java))
                }
                return true
            }

        })
        homeKeyEventBroadCastReceiver = HomeKeyEventBroadCastReceiver()
        homeKeyEventBroadCastReceiver?.register(this@BitmapActivity)
    }

    override fun initData(savedInstanceState: Bundle?) {
        RxJavaUtils.executeAsyncTask(object : RxAsyncTask<String, Bitmap?>("0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg") {
            override fun doInUIThread(t: Bitmap?) {
                imageView.setImageBitmap(t)
            }

            override fun doInIOThread(t: String?): Bitmap? {
                if (t != null) {
                    val options = BitmapFactory.Options()
                    options.inPreferredConfig = RandomUtil.randomEle(FLAG.f)
                    return BitmapFactory.decodeStream(assets.open(t), null, options)
                }
                return null
            }

        })
    }

    fun change(v: View) {
        initData(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        homeKeyEventBroadCastReceiver?.unregister(this@BitmapActivity)
    }
}
