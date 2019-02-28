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

package com.weyee.poswidget.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * @author wuqi by 2019/2/25.
 */
public class Utils {
    /**
     * get ListView height according to every children
     *
     * @param view
     * @return
     */
    public static int getListViewHeightBasedOnChildren(ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        ListAdapter adapter;
        int adapterCount;
        if (view != null && (adapter = view.getAdapter()) != null
                && (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    /**
     * get AbsListView height according to every children
     *
     * @param view
     * @return
     */
    public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
        ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }

        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }
}
