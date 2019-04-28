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

package com.weyee.sdk.aspectj;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 过滤重复点击
 *
 * @author wuqi by 2019/3/18.
 */
@Aspect
public class ClickHook {
    private static long lastClickTime;

        @Around("execution(* android.view.View.OnClickListener.onClick(..))")
//    @Around("execution(* android.widget.AdapterView.OnItemClickListener.onItemClick(..))")
//    @Around("call(* android..*.startActivity*(..))")
    public void clickHook(ProceedingJoinPoint joinPoint) {
        if (!isFastDoubleClick()) {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e("ClickHook", "重复点击,已过滤");
        }
    }

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        // 防止快速点击默认等待时长为900ms
        long DELAY_TIME = 900;
        if (0 < timeD && timeD < DELAY_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
