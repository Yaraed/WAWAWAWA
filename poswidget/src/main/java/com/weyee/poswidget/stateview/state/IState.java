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

package com.weyee.poswidget.stateview.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.weyee.poswidget.stateview.manager.StateEventListener;

/**
 * State生命周期管理
 */
public interface IState<T extends StateProperty> {

    String EMPTY = "empty_state";
    String EXCEPTION = "exception_state";
    String LAODING = "loading_state";
    String NETERROR = "net_error_state";
    String ERROR = "error_state";

    /**
     * StateView创建后，可以做一些操作
     */
    void onStateCreate(Context context, ViewGroup parent);


    /**
     * StateView显示后，可以做一些操作
     */
    void onStateResume();

    /**
     * StateView隐藏后，可以做一些操作
     */
    void onStatePause();


    /**
     * 获取当前状态
     *
     * @return
     */
    String getState();

    /**
     * 设置当前状态下的一些按钮操作回调
     *
     * @param listener
     */
    void setStateEventListener(StateEventListener listener);


    /**
     * 获取状态机的View
     *
     * @return
     */
    View getView();


    /**
     * 定制View里面控件的内容
     */
    void setViewProperty(T stateProperty);
}
