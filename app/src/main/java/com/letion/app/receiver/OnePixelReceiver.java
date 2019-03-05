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

package com.letion.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.weyee.possupport.arch.OnePixelActivity;

/**
 * @author wuqi by 2019/3/1.
 */
public class OnePixelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {    //屏幕关闭启动1像素Activity
            Intent it = new Intent(context, OnePixelActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
            System.out.println("打开1像素的页面");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {   //屏幕打开 结束1像素
            context.sendBroadcast(new Intent("FINISH"));
            Intent main = new Intent(Intent.ACTION_MAIN);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            main.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(main);
            System.out.println("关闭1像素的页面");
        }
    }
}
