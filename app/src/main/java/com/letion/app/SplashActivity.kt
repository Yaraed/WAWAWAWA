package com.letion.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.poswidget.stateview.view.LoadingView
import com.weyee.sdk.api.rxutil.RxJavaUtils
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarAlpha(this@SplashActivity, 112, true)
        BarUtils.setStatusBarVisibility(this@SplashActivity, false)

        val content = findViewById<ViewGroup>(android.R.id.content)
        val loadingView = LoadingView(this)

        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        content.addView(loadingView, params)

        val textView = TextView(this)
        textView.setTextColor(Color.parseColor("#999999"))
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setLineSpacing(textView.lineSpacingExtra, 1.5f)
        textView.text = String.format("v %s\n客服服务热线：400-606-0201", AppUtils.getAppVersionName())
        val params2 = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params2.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        params2.bottomMargin = SizeUtils.dp2px(20f)
        content.addView(textView, params2)
    }

    override fun onResume() {
        super.onResume()
        RxJavaUtils.delay(2L, TimeUnit.SECONDS)
            .`as`(RxLiftUtils.bindLifecycle(this))
            .subscribe {
                toMain()
            }
    }

    private fun toMain(){
        val animatorX = ObjectAnimator.ofFloat(window.decorView,"scaleX", 1f, 1.15f)
        val animatorY = ObjectAnimator.ofFloat(window.decorView,"scaleY", 1f, 1.15f)

        val animatorSet = AnimatorSet()
        animatorSet.setDuration(1000).play(animatorX).with(animatorY)
        animatorSet.start()
        animatorSet.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                // Preview Window设置的背景图如果不做处理，图片就会一直存在于内存中
                window.setBackgroundDrawable(null)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })

    }
}
