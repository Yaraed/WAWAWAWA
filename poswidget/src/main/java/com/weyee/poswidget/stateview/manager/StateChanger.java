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

package com.weyee.poswidget.stateview.manager;

import com.weyee.poswidget.stateview.state.StateProperty;

/**
 * 状态观察者
 */
public interface StateChanger {

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    boolean showState(String state);

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    boolean showState(StateProperty state);

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

}
