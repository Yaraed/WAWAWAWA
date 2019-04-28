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

import android.view.View;

/**
 * 核心UI界面
 */
public class ContentState extends BaseState {

    public static final String STATE = "ContentState";

    private String state = STATE;

    public ContentState(View coreView) {
        stateView = coreView;
    }

    /**
     * 支持创建多个CoreState时，需要指定不同的State，达到分离业务逻辑
     *
     * @param coreView
     * @param state
     */
    public ContentState(View coreView, String state) {
        stateView = coreView;
        this.state = state;
    }


    /**
     * 如果使用这个构造，需要重写{@link BaseState#getLayoutId()}方法
     */
    protected ContentState() { }


    @Override
    protected int getLayoutId() {

        try {
            throw new IllegalStateException(this + "没有返回布局文件Id");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    protected void onViewCreated(View stateView) {

    }


    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
