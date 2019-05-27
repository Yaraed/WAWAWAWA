/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.wuqi.a_gpuimage

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.base.ThreadPool
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.mediaretriever.Metadata
import com.weyee.sdk.player.OrientationSensor
import com.weyee.sdk.player.assist.InterEvent
import com.weyee.sdk.player.assist.OnVideoViewEventHandler
import com.weyee.sdk.player.entity.DataSource
import com.weyee.sdk.player.event.OnPlayerEventListener
import com.weyee.sdk.player.player.IPlayer
import com.weyee.sdk.player.widget.BaseVideoView
import com.weyee.sdk.router.Path
import com.weyee.sdk.util.Tools
import com.wuqi.a_gpuimage.play.ReceiverGroupManager
import kotlinx.android.synthetic.main.activity_video.*


@Route(path = Path.GPU + "Video")
class VideoActivity : BaseActivity<BasePresenter<BaseModel, IView>>(), OnPlayerEventListener {
    private var hasStart: Boolean = false

    private var userPause: Boolean = false
    private var isLandscape: Boolean = false

    private var orientationSensor: OrientationSensor? = null

    private var metadatas: MutableList<Metadata>? = null

    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun getResourceId(): Int = R.layout.activity_video

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        videoView.setOnPlayerEventListener(this)
        val receiverGroup = ReceiverGroupManager.get().getReceiverGroup(context)
        receiverGroup.groupValue.putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true)
        videoView.setReceiverGroup(receiverGroup)
        videoView.setEventHandler(object : OnVideoViewEventHandler() {
            override fun onAssistHandle(assist: BaseVideoView?, eventCode: Int, bundle: Bundle?) {
                super.onAssistHandle(assist, eventCode, bundle)
                when (eventCode) {
                    InterEvent.CODE_REQUEST_PAUSE -> {
                        userPause = true
                    }
                    DataInter.Event.EVENT_CODE_ERROR_SHOW -> {
                        videoView.stop()
                    }
                    DataInter.Event.EVENT_CODE_REQUEST_BACK -> {
                        if (isLandscape) {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        } else {
                            finish()
                        }
                    }
                    DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN -> {
                        requestedOrientation = if (isLandscape)
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        else
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                }
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {


//        RxJavaUtils.executeAsyncTask(object :
//            RxAsyncTask<String, MutableMap<String, Any>>("http://jiajunhui.cn/video/edwin_rolling_in_the_deep.flv") {
//            override fun doInUIThread(t: MutableMap<String, Any>?) {
//                imageView.setImageBitmap(t?.get("IMAGE") as Bitmap?)
//                metadatas = mutableListOf()
//                t?.asIterable()?.forEach {
//                    metadatas?.add(com.weyee.sdk.mediaretriever.Metadata(it.key, it.value))
//                }
//                (listView.adapter as BaseAdapter).notifyDataSetChanged()
//            }
//
//            override fun doInIOThread(t: String?): MutableMap<String, Any> {
//                return MediaRetrieverUtils.loadResource(t, null)
//            }
//
//        })

        listView.adapter = object : BaseAdapter() {

            @SuppressLint("ViewHolder")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_2, null)
                val test1 = view.findViewById<TextView>(android.R.id.text1)
                val test2 = view.findViewById<TextView>(android.R.id.text2)

                test1.text = getItem(position)?.key
                test2.text = getItem(position)?.value.toString()

                return view
            }

            override fun getItem(position: Int): Metadata? {
                return metadatas?.get(position)
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getCount(): Int {
                return metadatas?.size ?: 0
            }

        }

        orientationSensor = OrientationSensor(this, object : OrientationSensor.OnOrientationListener {
            override fun onPortrait(orientation: Int) {
                if (videoView.isInPlaybackState) {
                    requestedOrientation = orientation
                }
            }

            override fun onLandScape(orientation: Int) {
                if (videoView.isInPlaybackState) {
                    requestedOrientation = orientation
                }
            }

        })
        orientationSensor?.enable();
    }


    private fun initPlay() {
        if (!hasStart) {

            ThreadPool.run {

                val path = "http://jiajunhui.cn/video/edwin_rolling_in_the_deep.flv"

                val dataSource = DataSource(path)
                dataSource.title = "音乐和艺术如何改变世界"
                //videoView.setRenderType(IRender.RENDER_TYPE_SURFACE_VIEW)




                videoView.setDataSource(dataSource)
                videoView.start()
                hasStart = true
            }


        }
    }

    /**
     * 隐藏头部布局
     */
    override fun hasToolbar(): Boolean {
        return !super.hasToolbar()
    }

    override fun onPlayerEvent(eventCode: Int, bundle: Bundle?) {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        isLandscape = newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE

        updateVideo(isLandscape)
    }

    override fun onBackPressed() {
        if (isLandscape) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        val state = videoView.state

        if (state == IPlayer.STATE_PLAYBACK_COMPLETE) {
            return
        }
        if (videoView.isInPlaybackState) {
            videoView.pause()
        } else {
            videoView.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        val state = videoView.state

        if (state == IPlayer.STATE_PLAYBACK_COMPLETE) {
            return
        }
        if (videoView.isInPlaybackState) {
            if (!userPause) {
                videoView.resume()
            }
        } else {
            videoView.rePlay(0)
        }
        initPlay()
    }

    override fun onStart() {
        super.onStart()
        orientationSensor?.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationSensor?.disable()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }

    private fun updateVideo(landscape: Boolean) {
        val layoutParams = videoView.layoutParams as ViewGroup.LayoutParams
        if (landscape) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            layoutParams.width = Tools.getScreenWidth()
            layoutParams.height = layoutParams.width * 3 / 4

            val params = listView.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = layoutParams.height
            listView.layoutParams = params
        }
        videoView.layoutParams = layoutParams

        listView.visibility = if (landscape) View.GONE else View.VISIBLE
    }
}
