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

package com.weyee.possupport.repeatclick;

import android.view.View;

/**
 * 防止多次点击事件
 * @author wuqi by 2019/1/25.
 */
public abstract class OnFastClickListener extends BaseClickListener{

    // 防止快速点击默认等待时长为900ms
    private long DELAY_TIME = 900;
    private static long lastClickTime;

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick()){
            return;
        }
        onFastClick(v);
    }

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < DELAY_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 设置默认快速点击事件时间间隔
     * @param delay_time
     * @return
     */
    public OnFastClickListener setLastClickTime(long delay_time) {

        this.DELAY_TIME = delay_time;

        return this;
    }

    /**
     * 快速点击事件回调方法
     * @param v
     */
    public abstract void onFastClick(View v);
}
