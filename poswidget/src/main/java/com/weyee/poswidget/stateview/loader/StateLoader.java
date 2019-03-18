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

package com.weyee.poswidget.stateview.loader;

import android.view.View;
import com.weyee.poswidget.stateview.state.IState;

/**
 * 状态加载器，加载各种状态
 */
public interface StateLoader {


    /**
     * 注册一个状态器，如果有重复的状态改变器，则不添加
     *
     * @param changger
     */
    boolean addState(IState changger);

    /**
     * 如果对应的状态加载器
     *
     * @param state 状态
     */
    boolean removeState(String state);


    View getStateView(String state);
}
