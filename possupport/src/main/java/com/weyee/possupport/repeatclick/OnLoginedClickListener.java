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
 * 判断当前用户是否登录
 * @author wuqi by 2019/1/25.
 */
public abstract class OnLoginedClickListener extends BaseClickListener {
    @Override
    public void onClick(View v) {
        if (isLogined(v)){
            onLoginedClick(v);
        }else{
            onNoLoginedClick(v);
        }
    }

    /**
     * 判断当前用户是否登录
     * @param view
     * @return
     */
    public abstract boolean isLogined(View view);

    /**
     * 用户登录之后执行的逻辑
     * @param v
     */
    public abstract void onLoginedClick(View v);

    /**
     * 用户未登录执行点击事件
     */
    public abstract void onNoLoginedClick(View v);
}
