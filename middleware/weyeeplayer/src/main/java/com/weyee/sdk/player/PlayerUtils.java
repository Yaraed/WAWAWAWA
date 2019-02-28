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

package com.weyee.sdk.player;

import android.app.Application;
import com.weyee.sdk.player.config.PlayerConfig;
import com.weyee.sdk.player.config.PlayerLibrary;
import com.weyee.sdk.player.record.PlayRecordManager;

/**
 * @author wuqi by 2019/2/26.
 */
public class PlayerUtils {
    public static void init(Application application){
        //如果您想使用默认的网络状态事件生产者，请添加此行配置。
        //并需要添加权限 android.permission.ACCESS_NETWORK_STATE
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        //初始化库
        PlayerLibrary.init(application);

        //-------------------------------------------


        //播放记录的配置
        //开启播放记录
        PlayerConfig.playRecord(true);
        PlayRecordManager.setRecordConfig(
                new PlayRecordManager.RecordConfig.Builder()
                        .setMaxRecordCount(100)
                        //.setRecordKeyProvider()
                        //.setOnRecordCallBack()
                        .build());
    }
}
