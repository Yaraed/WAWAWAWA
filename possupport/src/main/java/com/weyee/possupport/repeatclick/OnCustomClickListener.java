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
 * 通用自定义View处理点击事件
 */
public abstract class OnCustomClickListener extends BaseClickListener {
    @Override
    public void onClick(View v) {
        if (isCorrect()){
            onCorrentClick(v);
        }else{
            onNoCorrentClick(v);
        }
    }

    /**
     * 判断是否逻辑通过
     * @return
     */
    public abstract boolean isCorrect();

    /**
     * 判断正确之后执行的逻辑请求
     * @param v
     */
    public abstract void onCorrentClick(View v);

    /**
     * 判断失败之后执行的逻辑请求
     * @param v
     */
    public abstract void onNoCorrentClick(View v);


}
