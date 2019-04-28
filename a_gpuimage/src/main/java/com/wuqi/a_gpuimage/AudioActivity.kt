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

import android.Manifest.permission.RECORD_AUDIO
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.sdk.audio.StreamAudioPlayer
import com.weyee.sdk.audio.StreamAudioRecorder
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_audio.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@Route(path = Path.GPU + "Audio")
class AudioActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private var mIsRecording: Boolean = false
    private var outputFile: File? = null
    private var outputStream: FileOutputStream? = null
    private var inputStream: FileInputStream? = null
    private lateinit var mStreamAudioRecorder: StreamAudioRecorder
    private lateinit var mStreamAudioPlayer: StreamAudioPlayer

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_audio

    override fun initView(savedInstanceState: Bundle?) {
        mBtnStart.setOnClickListener { start() }
        mBtnPlay.setOnClickListener { play() }
    }

    override fun initData(savedInstanceState: Bundle?) {
        mStreamAudioRecorder = StreamAudioRecorder.getInstance()
        mStreamAudioPlayer = StreamAudioPlayer.getInstance()
    }

    override fun onDestroy() {
        stop()
        stopPlay()
        super.onDestroy()
    }

    private fun start() {
        if (mIsRecording) {
            stop()
            mIsRecording = false
        }
        AndPermission.with(this)
            .runtime()
            .permission(Permission.WRITE_EXTERNAL_STORAGE, RECORD_AUDIO)
            .onGranted {
                run {
                    mIsRecording = true
                    mBtnStart.text = "暂停"
                    try {
                        outputFile = File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath
                                    + File.separator + System.nanoTime() + "stream.m4a"
                        )
                        outputFile?.createNewFile()
                        outputStream = FileOutputStream(outputFile)

                        mStreamAudioRecorder.start(object : StreamAudioRecorder.AudioDataCallback {
                            override fun onAudioData(data: ByteArray?, size: Int) {
                                Log.d("AMP", "amp " + calcAmp(data!!, size))
                                outputStream?.write(data, 0, size)
                            }

                            override fun onError() {
                            }
                        })
                    } catch (e: IOException) {
                        e.printStackTrace()
                        mBtnStart.post {
                            mBtnStart.text = "开始"
                            mIsRecording = false
                            ToastUtils.show("录制语音失败")
                        }
                    }
                }
            }
            .onDenied {
                ToastUtils.show("暂无权限")
            }
            .start()
    }

    /**
     * 停止录制
     */
    private fun stop() {
        mBtnStart.text = "开始"
        mStreamAudioRecorder.stop()
        try {
            outputStream?.close()
            outputStream = null
        }catch (e : IOException){
            e.printStackTrace()
        }
    }

    /**
     * 播放录制的音频
     */
    private fun play(){
        Observable.just(outputFile)
            .subscribeOn(Schedulers.io())
            .`as`(RxLiftUtils.bindLifecycle(this))
            .subscribe {
                try {
                    mStreamAudioPlayer.init()
                    inputStream = FileInputStream(it)
                    val buffer = ByteArray(2048)
                    var size: Int = -1
                    while ({size = inputStream!!.read(buffer);size}() > 0){
                        mStreamAudioPlayer.play(buffer,size)
                    }
                    inputStream!!.close()
                    mStreamAudioPlayer.release()
                }catch (e : IOException){
                    e.printStackTrace()
                }
            }
    }

    private fun stopPlay(){
        mStreamAudioPlayer.release()
        try {
            inputStream?.close()
            inputStream = null
        }catch (e : IOException){
            e.printStackTrace()
        }
    }

    private fun calcAmp(data: ByteArray, size: Int): Int {
        var amplitude = 0
        var i = 0
        while (i + 1 < size) {
            val value = ((data[i + 1].toInt() and 0x000000FF shl 8) + (data[i + 1].toInt() and 0x000000FF)).toShort()
            amplitude += Math.abs(value.toInt())
            i += 2
        }
        amplitude /= size / 2
        return amplitude / 2048
    }
}
