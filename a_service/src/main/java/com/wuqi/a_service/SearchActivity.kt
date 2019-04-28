package com.wuqi.a_service

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.BarUtils
import com.weyee.sdk.toast.ToastUtils

class SearchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        BarUtils.setStatusBarAlpha(this@SearchActivity, 112, true)
        BarUtils.setStatusBarVisibility(this@SearchActivity,true)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
    }

    fun jump(v: View) {
        onSearchRequested()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        //如果是从搜索开启的这个页面就获取到搜索框中输入的内容
        if (Intent.ACTION_SEARCH == intent.action) {
            //获取到在搜索框中输入的内容
            val query = intent.getStringExtra(SearchManager.QUERY)
            //执行搜索的方法
            ToastUtils.show(query)
        }
        println("onResume")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        println("onPostCreate")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        println("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        println("onSaveInstanceState")
    }


}
