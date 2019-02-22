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
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wuqi by 2019/2/22.
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * 设置数据
     *
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    public abstract void setData(@NonNull T data, int position);

    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    protected void onRelease() {

    }
}
