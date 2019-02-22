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

package com.weyee.sdk.multitype;

import android.view.View;
import androidx.annotation.NonNull;

/**
 * item 的点击事件
 *
 * @author wuqi by 2019/2/22.
 */
public interface OnRecyclerViewItemClickListener<T> {
    /**
     * item 被点击
     *
     * @param view     被点击的 {@link View}
     * @param viewType 布局类型
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    void onItemClick(@NonNull View view, int viewType, @NonNull T data, int position);
}
