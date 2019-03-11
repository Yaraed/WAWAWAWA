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

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.DefaultAdapter
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.multitype.OnRecyclerViewItemClickListener
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_ble_helper.*
import top.wuhaojie.bthelper.BtHelperClient
import top.wuhaojie.bthelper.MessageItem
import top.wuhaojie.bthelper.OnSearchDeviceListener
import top.wuhaojie.bthelper.OnSendMessageListener

@Route(path = Path.BLE + "BleHelper")
class BleHelperActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private lateinit var btHelper: BtHelperClient


    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun getResourceId(): Int = R.layout.activity_ble_helper

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).size(1).build())
//        recyclerView.adapter = BleHelperAdapter(null, Callback1 {
//            btHelper.sendMessage(it, MessageItem(it), true, object : OnSendMessageListener {
//
//                override fun onSuccess(p0: Int, p1: String?) {
//                    ToastUtils.show(p1 ?: "发送成功")
//                }
//
//                override fun onConnectionLost(p0: Exception?) {
//                    ToastUtils.show(p0?.message ?: "发送失败")
//                }
//
//                override fun onError(p0: Exception?) {
//                    ToastUtils.show(p0?.message ?: "发送失败")
//                }
//
//            })
//        })
        recyclerView.adapter = object : DefaultAdapter<BluetoothDevice>(null,
            OnRecyclerViewItemClickListener<BluetoothDevice> { _, _, data, _ ->
                btHelper.sendMessage(data.address, MessageItem(data.name), true, object : OnSendMessageListener {

                    override fun onSuccess(p0: Int, p1: String?) {
                        ToastUtils.show(p1 ?: "发送成功")
                    }

                    override fun onConnectionLost(p0: Exception?) {
                        ToastUtils.show(p0?.message ?: "发送失败")
                    }

                    override fun onError(p0: Exception?) {
                        ToastUtils.show(p0?.message ?: "发送失败")
                    }

                })
            }) {
            override fun getHolder(v: View, viewType: Int): BaseHolder<BluetoothDevice> {
                return object : BaseHolder<BluetoothDevice>(v) {
                    override fun setData(data: BluetoothDevice, position: Int) {
                        itemView.findViewById<TextView>(android.R.id.text1).text = data.name
                        itemView.findViewById<TextView>(android.R.id.text2).text = data.address
                    }
                }
            }

            override fun getLayoutId(viewType: Int): Int = android.R.layout.simple_list_item_2

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        btHelper = BtHelperClient.from(context)
        btHelper.searchDevices(object : OnSearchDeviceListener {
            override fun onNewDeviceFounded(p0: BluetoothDevice?) {
                if (p0 != null)
                    (recyclerView.adapter as DefaultAdapter<BluetoothDevice>).addData(p0)
            }

            override fun onStartDiscovery() {
                ToastUtils.show("正在扫描")
            }

            override fun onSearchCompleted(p0: MutableList<BluetoothDevice>?, p1: MutableList<BluetoothDevice>?) {
                ToastUtils.show("搜索完毕")
            }

            override fun onError(p0: java.lang.Exception?) {
                p0?.printStackTrace()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        btHelper.close()
    }
}
