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

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.weyee.poswidget.stateview.state.IState;

import java.util.HashMap;

/**
 * 状态仓库
 */
public class StateRepository implements StateLoader {

    public static void registerState(String state, Class clazz) {
        stateClazzMap.put(state, clazz);
    }

    public static void unregisterState(String state) {
        stateClazzMap.remove(state);
    }

    /**
     * 用于映射State和具体State对象
     */
    protected HashMap<String, IState> stateMap = new HashMap<>(5);
    protected static HashMap<String, Class> stateClazzMap = new HashMap<String, Class>(5);


    protected Context mContext;

    public StateRepository(Context context) {
        mContext = context;
    }

    @Override
    public boolean addState(IState changger) {
        if (changger != null && !TextUtils.isEmpty(changger.getState())) {
            stateMap.put(changger.getState(), changger);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeState(String state) {
        if (!TextUtils.isEmpty(state)) {
            stateMap.remove(state);
            return true;
        }
        return false;
    }


    public IState get(String state) {

        IState istate = stateMap.get(state);
        if (istate != null) {
            return istate;
        }

        Class clazz = stateClazzMap.get(state);
        if (clazz != null) {
            try {
                istate = (IState) clazz.newInstance();
                addState(istate);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return istate;
    }

    @Override
    public View getStateView(String state) {
        IState stateChanger = get(state);
        if (stateChanger != null) {
            return stateChanger.getView();
        }
        return null;
    }


    public HashMap<String, IState> getStateMap() {
        return stateMap;
    }


    public void clear() {
        stateMap.clear();
    }

}
