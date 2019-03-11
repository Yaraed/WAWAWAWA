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

package com.wuqi.a_intent

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.widget.PopupMenu
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_permission.*
import java.io.File

@Route(path = Path.Intent + "Permission")
class PermissionActivity : BaseActivity<BasePresenter<BaseModel, IView>>(), View.OnClickListener {

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_permission

    override fun initView(savedInstanceState: Bundle?) {
        btn_request_camera.setOnClickListener(this)
        btn_request_contact.setOnClickListener(this)
        btn_request_location.setOnClickListener(this)
        btn_request_calendar.setOnClickListener(this)
        btn_request_microphone.setOnClickListener(this)
        btn_request_phone.setOnClickListener(this)
        btn_request_sensors.setOnClickListener(this)
        btn_request_sms.setOnClickListener(this)
        btn_setting.setOnClickListener(this)
        btn_install.setOnClickListener(this)
        btn_overlay.setOnClickListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }


    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_request_camera -> {
                requestPermission(*Permission.Group.CAMERA)
            }
            R.id.btn_request_contact -> {
                val popupMenu = createMenu(v, resources.getStringArray(R.array.contacts))
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    val order = item.itemId
                    when (order) {
                        0 -> {
                            requestPermission(Permission.READ_CONTACTS)
                        }
                        1 -> {
                            requestPermission(Permission.WRITE_CONTACTS)
                        }
                        2 -> {
                            requestPermission(Permission.GET_ACCOUNTS)
                        }
                        3 -> {
                            requestPermission(*Permission.Group.CONTACTS)
                        }
                    }
                    true
                })
                popupMenu.show()
            }
            R.id.btn_request_location -> {
                val popupMenu = createMenu(v, resources.getStringArray(R.array.location))
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    val order = item.itemId
                    when (order) {
                        0 -> {
                            requestPermission(Permission.ACCESS_FINE_LOCATION)
                        }
                        1 -> {
                            requestPermission(Permission.ACCESS_COARSE_LOCATION)
                        }
                        2 -> {
                            requestPermission(*Permission.Group.LOCATION)
                        }
                    }
                    true
                })
                popupMenu.show()
            }
            R.id.btn_request_calendar -> {
                val popupMenu = createMenu(v, resources.getStringArray(R.array.calendar))
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    val order = item.order
                    when (order) {
                        0 -> {
                            requestPermission(Permission.READ_CALENDAR)
                        }
                        1 -> {
                            requestPermission(Permission.WRITE_CALENDAR)
                        }
                        2 -> {
                            requestPermission(*Permission.Group.CALENDAR)
                        }
                    }
                    true
                })
                popupMenu.show()
            }
            R.id.btn_request_microphone -> {
                requestPermission(*Permission.Group.MICROPHONE)
            }
            R.id.btn_request_storage -> {
                val popupMenu = createMenu(v, resources.getStringArray(R.array.storage))
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    val order = item.order
                    when (order) {
                        0 -> {
                            requestPermission(Permission.READ_EXTERNAL_STORAGE)
                        }
                        1 -> {
                            requestPermission(Permission.WRITE_EXTERNAL_STORAGE)
                        }
                        2 -> {
                            requestPermission(*Permission.Group.STORAGE)
                        }
                    }
                    true
                })
                popupMenu.show()
            }
            R.id.btn_request_phone -> {

            }
            R.id.btn_request_sensors -> {
                requestPermission(*Permission.Group.SENSORS)
            }
            R.id.btn_request_sms -> {
                val popupMenu = createMenu(v, resources.getStringArray(R.array.sms))
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    val order = item.order
                    when (order) {
                        0 -> {
                            requestPermission(Permission.SEND_SMS)
                        }
                        1 -> {
                            requestPermission(Permission.RECEIVE_SMS)
                        }
                        2 -> {
                            requestPermission(Permission.READ_SMS)
                        }
                        3 -> {
                            requestPermission(Permission.RECEIVE_WAP_PUSH)
                        }
                        4 -> {
                            requestPermission(Permission.RECEIVE_MMS)
                        }
                        5 -> {
                            requestPermission(*Permission.Group.SMS)
                        }
                    }
                    true
                })
                popupMenu.show()
            }
            R.id.btn_setting -> {
                setPermission()
            }
            R.id.btn_install -> {
                requestPermissionForInstallPackage()
            }
            R.id.btn_overlay -> {
                requestPermissionForAlertWindow()
            }
        }
    }

    /**
     * Request permissions.
     */
    private fun requestPermission(vararg permissions: String) {
        AndPermission.with(this)
            .runtime()
            .permission(*permissions)
            .rationale(RuntimeRationale())
            .onGranted { ToastUtils.show(R.string.successfully) }
            .onDenied {
                ToastUtils.show(R.string.failure)
                if (AndPermission.hasAlwaysDeniedPermission(this@PermissionActivity, permissions.asList())) {
                    showSettingDialog(this@PermissionActivity, permissions.asList())
                }
            }
            .start()
    }

    /**
     * Display setting dialog.
     */
    fun showSettingDialog(context: Context, permissions: List<String>) {
        val permissionNames = Permission.transformText(context, permissions)
        val message =
            context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames))

        AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(R.string.title_dialog)
            .setMessage(message)
            .setPositiveButton(R.string.setting) { dialog, which -> setPermission() }
            .setNegativeButton(R.string.cancel, { dialog, which -> })
            .show()
    }

    /**
     * Set permissions.
     */
    private fun setPermission() {
        AndPermission.with(this)
            .runtime()
            .setting()
            .onComeback { ToastUtils.show(R.string.message_setting_comeback) }
            .start()
    }

    /**
     * Request to read and write external storage permissions.
     */
    private fun requestPermissionForInstallPackage() {
        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .rationale(RuntimeRationale())
            .onGranted { ToastUtils.show("准备安装") }
            .onDenied { ToastUtils.show(R.string.message_install_failed) }
            .start()
    }

    /**
     * Install package.
     */
    private fun installPackage() {
        AndPermission.with(this)
            .install()
            .file(File(Environment.getExternalStorageDirectory(), "android.apk"))
            .rationale(InstallRationale())
            .onGranted { ToastUtils.show("安装apk") }
            .onDenied { ToastUtils.show("安装失败") }
            .start()
    }

    private fun requestPermissionForAlertWindow() {
        AndPermission.with(this)
            .overlay()
            .rationale(OverlayRationale())
            .onGranted {
                showAlertWindow()
            }
            .onDenied { ToastUtils.show(R.string.message_overlay_failed) }
            .start()
    }

    private fun showAlertWindow() {
        val backHome = Intent(Intent.ACTION_MAIN)
        backHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        backHome.addCategory(Intent.CATEGORY_HOME)
        startActivity(backHome)
    }

    /**
     * Create menu.
     */
    private fun createMenu(v: View, menuArray: Array<String>): PopupMenu {
        val popupMenu = PopupMenu(this, v)
        val menu = popupMenu.menu
        for (i in menuArray.indices) {
            val menuText = menuArray[i]
            menu.add(0, i, i, menuText)
        }
        return popupMenu
    }
}
