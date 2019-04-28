package com.wuqi.a_service

import android.os.Bundle
import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SizeUtils
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.poswidget.layout.QMUILayoutHelper
import com.weyee.sdk.router.Path
import kotlinx.android.synthetic.main.activity_uilayout.*

@Route(path = Path.Service + "UILayout")
class UILayoutActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private var radius: Int = 15
    private var alpha: Float = 0.5f
    private var elevation: Int = 10
    private var type: Int = QMUILayoutHelper.HIDE_RADIUS_SIDE_NONE
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_uilayout

    override fun initView(savedInstanceState: Bundle?) {
        //layout.setRadius(5, QMUILayoutHelper.HIDE_RADIUS_SIDE_NONE)
        layout.shadowColor = R.color.config_color_white
        test_seekbar_radius.progress = radius
        test_seekbar_elevation.progress = elevation
        test_seekbar_alpha.progress = (alpha * 100).toInt()
        change()
    }

    override fun initData(savedInstanceState: Bundle?) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            run {
                when (checkedId) {
                    R.id.hide_radius_none -> {
                        type = QMUILayoutHelper.HIDE_RADIUS_SIDE_NONE
                    }
                    R.id.hide_radius_top -> {
                        type = QMUILayoutHelper.HIDE_RADIUS_SIDE_TOP
                    }
                    R.id.hide_radius_right -> {
                        type = QMUILayoutHelper.HIDE_RADIUS_SIDE_RIGHT
                    }
                    R.id.hide_radius_bottom -> {
                        type = QMUILayoutHelper.HIDE_RADIUS_SIDE_BOTTOM
                    }
                    R.id.hide_radius_left -> {
                        type = QMUILayoutHelper.HIDE_RADIUS_SIDE_LEFT
                    }
                }
                change()
            }
        }
        test_seekbar_radius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                radius = progress
                change()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        test_seekbar_alpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                alpha = progress / 100f
                change()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        test_seekbar_elevation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                elevation = progress
                change()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }


    private fun change() {
        layout.setRadiusAndShadow(SizeUtils.dp2px(radius.toFloat()), type, SizeUtils.dp2px(elevation.toFloat()), alpha)
    }

}
