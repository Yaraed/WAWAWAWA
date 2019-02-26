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

package com.weyee.poswidget.spinner;

import android.content.Context;

import java.util.List;

/**
 * Spinner适配器
 * @author wuqi by 2019/2/25.
 */
public class MaterialSpinnerAdapter<T> extends MaterialSpinnerBaseAdapter {

    private final List<T> mItems;

    public MaterialSpinnerAdapter(Context context, List<T> items) {
        super(context);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() - 1 : 0;
    }

    @Override
    public T getItem(int position) {
        if (position >= getSelectedIndex()) {
            return mItems.get(position + 1);
        } else {
            return mItems.get(position);
        }
    }

    @Override
    public T get(int position) {
        if (mItems != null && position >= 0 && position <= mItems.size() - 1) {
            return mItems.get(position);
        } else {
            return null;
        }
    }

    @Override
    public List<T> getItems() {
        return mItems;
    }

}