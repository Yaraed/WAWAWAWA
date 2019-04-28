package com.wuqi.a_service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.permission.GeoIntents
import com.weyee.sdk.permission.ShortcutIntent
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission

@Route(path = Path.Service + "Location")
class LocationActivity : BaseActivity<BasePresenter<BaseModel, IView>>(), LocationListener {
    private var lm: LocationManager? = null
    private var latitude = 0f
    private var longitude = 0f

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_location


    override fun initView(savedInstanceState: Bundle?) {
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        lm?.removeUpdates(this)
        super.onDestroy()
    }

    override fun onLocationChanged(location: Location?) {
        latitude = location?.latitude?.toFloat() ?: 0f
        longitude = location?.longitude?.toFloat() ?: 0f
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    @SuppressLint("MissingPermission")
    fun start(v: View) {
        AndPermission.with(this@LocationActivity)
            .runtime().permission(Permission.Group.LOCATION)
            .onGranted {
                if (lm?.allProviders?.contains(LocationManager.NETWORK_PROVIDER) == true && lm?.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    ) == true
                ) {
                    lm?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, this)
                } else if (lm?.allProviders?.contains(LocationManager.GPS_PROVIDER) == true && lm?.isProviderEnabled(
                        LocationManager.GPS_PROVIDER
                    ) == true
                ) {
                    lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, this)
                } else {
                    val criteria = Criteria()
                    criteria.accuracy = Criteria.ACCURACY_FINE
                    criteria.isCostAllowed = true
                    criteria.powerRequirement = Criteria.POWER_HIGH
                    criteria.isSpeedRequired = true
                    lm?.requestLocationUpdates(lm?.getBestProvider(criteria, true), 0L, 0F, this)
                }
            }
            .onDenied { ToastUtils.show("暂无权限") }
            .start()
    }

    fun jump(v: View) {
        startActivity(
            GeoIntents.newMapsIntent(latitude, longitude)
        )
    }

    fun add(v: View) {
        if (!ShortcutIntent.hasShortcut(this@LocationActivity)) {
            ShortcutIntent.addShortcut(this@LocationActivity, R.drawable.ic_android_black_24dp)
        }
    }

    fun del(v: View) {
        if (ShortcutIntent.hasShortcut(this@LocationActivity)) {
            ShortcutIntent.delShortcut(this@LocationActivity)
        }
    }
}
