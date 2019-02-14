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

package com.weyee.sdk.permission;

import android.provider.Settings;

/**
 * intent调用设置页面 跳转参数大全
 * @author wuqi by 2019/2/13.
 */
public enum IntentEnum {
    ONE("跳转到设置界面",Settings.ACTION_SETTINGS),
    TWO("跳转系统的辅助功能界面",Settings.ACTION_ACCESSIBILITY_SETTINGS),
    THREE("添加帐户【测试跳转到微信登录界面】",Settings.ACTION_ADD_ACCOUNT),
    FOUR("飞行模式设置界面",Settings.ACTION_AIRPLANE_MODE_SETTINGS),
    FIVE("无线网设置界面",Settings.ACTION_WIFI_SETTINGS),
    SIX("跳转Wifi列表设置",Settings.ACTION_WIFI_SETTINGS),
    SEVEN("跳转 APN设置界面",Settings.ACTION_APN_SETTINGS),
    EIGHT("根据包名跳转到系统自带的应用程序信息界面",Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
    NINE("跳转开发人员选项界面",Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
    TEN("跳转应用程序列表界面",Settings.ACTION_APPLICATION_SETTINGS),
    ELEVEN("跳转到应用程序界面【所有的】",Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS),
    TWELVE("跳转到应用程序列表界面【已安装的】",Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS),
    THIRTEEN("跳转系统的蓝牙设置界面",Settings.ACTION_BLUETOOTH_SETTINGS),
    FOURTEEN("跳转到移动网络设置界面",Settings.ACTION_DATA_ROAMING_SETTINGS),
    FIFTEEN("跳转日期时间设置界面",Settings.ACTION_DATE_SETTINGS),
    SIXTEEN("跳转手机状态界面",Settings.ACTION_DEVICE_INFO_SETTINGS),
    SEVENTEEN("跳转手机显示界面",Settings.ACTION_DISPLAY_SETTINGS),
    EIGHTEEN("显示屏幕保护程序【API 18及以上】",Settings.ACTION_DREAM_SETTINGS),
    NINETEEN("跳转语言和输入设备",Settings.ACTION_INPUT_METHOD_SETTINGS),
    TWENTY("跳转语言选择界面",Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS),
    TWENTYONE("跳转存储设置界面【内部存储】",Settings.ACTION_INTERNAL_STORAGE_SETTINGS),
    TWENTYTWO("跳转 存储设置 【记忆卡存储】",Settings.ACTION_MEMORY_CARD_SETTINGS),
    TWENTYTHREE("跳转语言选择界面【仅有English 和 中文两种选择】",Settings.ACTION_LOCALE_SETTINGS),
    TWENTYFOUR("跳转位置服务界面【管理已安装的应用程序】",Settings.ACTION_LOCATION_SOURCE_SETTINGS),
    TWENTYFIVE("跳转到显示设置选择网络运营商",Settings.ACTION_NETWORK_OPERATOR_SETTINGS),
    TWENTYSIX("显示NFC共享设置",Settings.ACTION_NFCSHARING_SETTINGS),
    TWENTYSEVEN("显示NFC设置。这显示了用户界面,允许NFC打开或关闭",Settings.ACTION_NFC_SETTINGS),
    TWENTYEIGHT("跳转到备份和重置界面",Settings.ACTION_PRIVACY_SETTINGS),
    TWENTYNINE("跳转快速启动设置界面",Settings.ACTION_QUICK_LAUNCH_SETTINGS),
    THIRTY("跳转到搜索设置界面",Settings.ACTION_SEARCH_SETTINGS),
    THIRTYONE("跳转到安全设置界面",Settings.ACTION_SECURITY_SETTINGS),
    THIRTYTWO("跳转到声音设置界面",Settings.ACTION_SOUND_SETTINGS),
    THIRTYTHREE("跳转账户同步界面",Settings.ACTION_SYNC_SETTINGS),
    THIRTYFOUR("跳转用户字典界面",Settings.ACTION_USER_DICTIONARY_SETTINGS),
    THIRTYFIVE("跳转到IP设定界面",Settings.ACTION_WIFI_IP_SETTINGS),
    THIRTYSIX("跳转投射屏幕页面","android.settings.WIFI_DISPLAY_SETTINGS"),
    THIRTYSEVEN("跳转权限设置页面",null);

    private String label;
    private String path;

    IntentEnum(String label, String path) {
        this.label = label;
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
