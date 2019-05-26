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

package com.weyee.sdk.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.weyee.sdk.util.Tools;

/**
 * 右上角的菜单栏
 *
 * @author wuqi by 2019/2/22.
 */
public class MenuDialog extends BaseDialog {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public MenuDialog(@NonNull Context context) {
        super(context, R.style.QMUI_LoadingDialog);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.mrmo_menu_dialog, null);
        setContentView(inflate);
        setViewLocation();
        recyclerView = inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    protected void setViewLocation() {
        Window window = this.getWindow();
        window.setGravity(Gravity.RIGHT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (Tools.getScreenWidth() * 0.4);
        window.setAttributes(params);
    }

    public MenuDialog setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }
}
