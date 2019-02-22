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

package com.letion.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.weyee.sdk.dialog.BaseDialog;

/**
 * 很友好的加载弹窗
 *
 * @author wuqi by 2019/2/22.
 */
public class LoadingDialog extends BaseDialog {
    private TextView tvTips;
    private String tips;

    public LoadingDialog(@NonNull Context context, String tips) {
        super(context, R.style.QMUI_LoadingDialog);
        this.tips = tips;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        setContentView(inflate);
        tvTips = inflate.findViewById(R.id.tv_tips);
        tvTips.setText(tips);
    }

    public void setTvTips(String tips) {
        if (tvTips != null) {
            tvTips.setText(tips);
        }
    }
}
