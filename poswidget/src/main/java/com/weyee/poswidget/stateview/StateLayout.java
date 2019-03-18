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

package com.weyee.poswidget.stateview;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.weyee.poswidget.stateview.loader.StateLoader;
import com.weyee.poswidget.stateview.loader.StateRepository;
import com.weyee.poswidget.stateview.manager.StateChanger;
import com.weyee.poswidget.stateview.manager.StateEventListener;
import com.weyee.poswidget.stateview.manager.StateManager;
import com.weyee.poswidget.stateview.state.IState;
import com.weyee.poswidget.stateview.state.StateProperty;

/**
 * TODO 如果动态添加子view，暂时没有好的办法禁止
 * 建议不要使用addView()方法，添加子view，尽量使用addState的方式注册
 */
public class StateLayout extends FrameLayout implements StateChanger, StateLoader {

    private StateManager stateManager;

    public StateLayout(Context context, StateRepository repository) {
        super(context);
        init(context, repository);

    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, new StateRepository(context));
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, new StateRepository(context));
    }

    @TargetApi(21)
    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, new StateRepository(context));
    }

    private void init(Context context, StateRepository repository) {
        stateManager = StateManager.newInstance(context, repository, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            try {
                throw new IllegalStateException("StateLayout can have only one direct child");

            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else if (getChildCount() == 1) {
            loadCoreView(getChildAt(0));
        }
    }

    private void loadCoreView(View child) {
        if (child != null) {
            stateManager.setContentView(child);
        }
    }

    public void removeAllState() {
        stateManager.removeAllState();
    }

    @Override
    public boolean showState(String state) {
        return stateManager.showState(state);
    }

    @Override
    public boolean showState(StateProperty state) {
        return stateManager.showState(state);
    }

    @Override
    public void setStateEventListener(StateEventListener listener) {
        stateManager.setStateEventListener(listener);
    }

    @Override
    public String getState() {
        return stateManager.getState();
    }

    @Override
    public boolean addState(IState changger) {
        return stateManager.addState(changger);
    }

    @Override
    public boolean removeState(String state) {
        return stateManager.removeState(state);
    }

    @Override
    public View getStateView(String state) {
        return stateManager.getStateView(state);
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}