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

import android.content.Context;
import android.os.Build;

/**
 * 跳转应用权限页面
 * @author wuqi by 2019/2/13.
 */
public class PermissionIntents {
    public static void toPermissionSetting(Context context){
        // 权限设置页面，适配各大厂商机型
        switch (Build.MANUFACTURER){
            case "HUAWEI":
                Utils.goHuaWeiMainager(context);
                break;
            case "vivo":
                Utils.goVivoMainager(context);
                break;
            case "OPPO":
                Utils.goOppoMainager(context);
                break;
            case "Coolpad":
                Utils.goCoolpadMainager(context);
                break;
            case "Meizu":
                Utils.goMeizuMainager(context);
                break;
            case "Xiaomi":
                Utils.goXiaoMiMainager(context);
                break;
            case "samsung":
                Utils.goSangXinMainager(context);
                break;
            case "Sony":
                Utils.goSonyMainager(context);
                break;
            case "LG":
                Utils.goLGMainager(context);
                break;
            default:
                Utils.goIntentSetting(context);
                break;
        }
    }
}
