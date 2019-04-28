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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.weyee.poswidget.stateview.manager.StateEventListener;

/**
 * 基础状态器，加载一些界面
 * 所有的状态机都要实现这个类
 */
public abstract class BaseState<T extends StateProperty> implements IState<T> {

    public static final String STATE = "NONE";

    protected Context context;

    /**
     * 状态View
     */
    protected View stateView;

    /**
     * 状态属性：动态文案
     */
    protected T stateProperty;

    /**
     * 视图状态里面的按钮监听
     */
    protected StateEventListener stateEventListener;
    protected ViewGroup mOverallView;

    public BaseState() {
    }

    @Override
    public void onStateCreate(Context context, ViewGroup parent) {
        this.context = context;
        this.mOverallView = parent;
        stateView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        onViewCreated(stateView);

    }

    @Override
    public View getView() {
        return stateView;
    }

    protected abstract int getLayoutId();


    @Override
    public void setViewProperty(T stateProperty) {
        this.stateProperty = stateProperty;
    }

    /**
     * 获取当前状态view
     *
     * @param stateView
     * @return
     */
    protected abstract void onViewCreated(View stateView);

    @Override
    public void onStateResume() {
    }

    @Override
    public void onStatePause() {

    }

    public void setStateEventListener(StateEventListener listener) {
        this.stateEventListener = listener;
    }


    public Context getContext() {
        return context;
    }
}
