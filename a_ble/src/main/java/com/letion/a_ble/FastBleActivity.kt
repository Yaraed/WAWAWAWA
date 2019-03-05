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

package com.letion.a_ble

import android.app.ProgressDialog
import android.bluetooth.BluetoothGatt
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SpanUtils
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.api.rxutil.DisposablePool
import com.weyee.sdk.api.rxutil.RxJavaUtils
import com.weyee.sdk.dialog.MessageDialog
import com.weyee.sdk.log.Environment
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.DefaultAdapter
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.multitype.OnRecyclerViewItemClickListener
import com.weyee.sdk.permission.PermissionIntents
import com.weyee.sdk.router.BleNavigation
import com.weyee.sdk.toast.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_fast_ble.*

@Route(path = BleNavigation.MODULE_NAME + "FastBle")
class FastBleActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private var messageDialog: MessageDialog? = null
    private var progressDialog: ProgressDialog? = null
    private var list: MutableList<BleDevice>? = null

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_fast_ble

    override fun initView(savedInstanceState: Bundle?) {
        BleManager.getInstance().init(application)
        BleManager.getInstance()
            .enableLog(Environment.isDebug())
            .setReConnectCount(1, 5000).operateTimeout = 5000

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration.Builder(context).build())
        recyclerView.adapter = object : DefaultAdapter<BleDevice>(list,
            OnRecyclerViewItemClickListener<BleDevice> { _, _, data, _ ->
                if(BleManager.getInstance().isConnected(data)){
                    BleManager.getInstance().disconnect(data)
                    recyclerView.adapter?.notifyDataSetChanged()
                }else{
                    BleManager.getInstance().connect(data, object : BleGattCallback() {
                        override fun onStartConnect() {
                            progressDialog?.setMessage("正在连接...")
                            progressDialog?.show()
                        }

                        override fun onDisConnected(
                            isActiveDisConnected: Boolean,
                            device: BleDevice?,
                            gatt: BluetoothGatt?,
                            status: Int
                        ) {
                            ToastUtils.show("断开连接")
                            recyclerView.adapter?.notifyDataSetChanged()
                        }

                        override fun onConnectSuccess(bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                            progressDialog?.dismiss()
                            ToastUtils.show("连接成功")
                            recyclerView.adapter?.notifyDataSetChanged()
                        }

                        override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                            progressDialog?.dismiss()
                            ToastUtils.show("连接失败")
                            recyclerView.adapter?.notifyDataSetChanged()
                        }

                    })
                }
            }) {
            override fun getHolder(v: View, viewType: Int): BaseHolder<BleDevice> {
                return object : BaseHolder<BleDevice>(v) {
                    override fun setData(data: BleDevice, position: Int) {
                        val text1 = itemView.findViewById<TextView>(android.R.id.text1)
                        val text2 = itemView.findViewById<TextView>(android.R.id.text2)
                        text1.text = data.name
                        text2.text = SpanUtils().append(data.mac).append(if(BleManager.getInstance().isConnected(data))"已连接" else "").setHorizontalAlign(
                            Layout.Alignment.ALIGN_RIGHT).create()
                    }

                }
            }

            override fun getLayoutId(viewType: Int): Int = android.R.layout.simple_list_item_2

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (!BleManager.getInstance().isSupportBle) {
            ToastUtils.show("当前设备不支持蓝牙")
            return
        }
        messageDialog = MessageDialog(context)
        if (!BleManager.getInstance().isBlueEnable) {
            messageDialog?.setMsg("当前设置未打开蓝牙，是否打开？")
            messageDialog?.setOnClickConfirmListener {
                // 异步
                BleManager.getInstance().enableBluetooth()
                DisposablePool.get().add("polling", RxJavaUtils.polling(500, 1000, Consumer {
                    if (BleManager.getInstance().isBlueEnable) {
                        DisposablePool.get().remove("polling")
                        discoverBle()
                    }
                }))

            }
            messageDialog?.setOnClickCancelListener { finish() }
            messageDialog?.show()
        } else {
            discoverBle()
        }
    }

    /**
     * 加载蓝牙
     */
    private fun discoverBle() {
        AndPermission.with(context)
            .runtime().permission(Permission.Group.LOCATION)
            .onGranted {
                BleManager.getInstance().scan(object : BleScanCallback() {
                    override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                        progressDialog?.dismiss()
                    }

                    override fun onScanStarted(success: Boolean) {
                        progressDialog = ProgressDialog.show(context, null, "加载中...", true, false)
                    }

                    override fun onScanning(bleDevice: BleDevice?) {
                        (recyclerView.adapter as DefaultAdapter<BleDevice>).addData(bleDevice)
                    }

                })
            }
            .onDenied {
                messageDialog?.setMsg("暂无蓝牙权限，请前往打开")
                messageDialog?.show()
                messageDialog?.setOnClickConfirmListener {
                    PermissionIntents.toPermissionSetting(context)
                }

            }.start()
    }

    override fun onDestroy() {
        BleManager.getInstance().destroy()
        super.onDestroy()
    }
}
