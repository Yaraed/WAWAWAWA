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

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author wuqi by 2019/2/28.
 */
public class MessageDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvConfirm;
    private View menuView;

    private View.OnClickListener onClickConfirmListener, onClickCancelListener;

    public MessageDialog(Context context) {
        super(context);
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        View rootView = getLayoutInflater().inflate(R.layout.mrmo_message_dialog, null, false);

        setContentView(rootView);
        setViewLocation();
        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvCancel = rootView.findViewById(R.id.tvCancel);
        tvConfirm = rootView.findViewById(R.id.tvConfirm);
        menuView = rootView.findViewById(R.id.menuView);

        tvCancel.setOnClickListener(v -> {
            dismiss();
            if (onClickCancelListener != null) {
                onClickCancelListener.onClick(v);
            }
        });
        tvConfirm.setOnClickListener(v -> {
            dismiss();
            if (onClickConfirmListener != null) {
                onClickConfirmListener.onClick(v);
            }
        });
    }

    public void setMsg(CharSequence msg) {
        tvTitle.setText(msg);
    }

    public void setOnClickConfirmListener(View.OnClickListener onClickListener) {
        this.onClickConfirmListener = onClickListener;
    }

    public void setOnClickCancelListener(View.OnClickListener onClickListener) {
        this.onClickCancelListener = onClickListener;
    }

    public void setConfirmText(String s) {
        tvConfirm.setText(s);
    }

    public void setConfirmColor(int c) {
        tvConfirm.setTextColor(c);
    }

    public void setCancelText(String s) {
        tvCancel.setText(s);
    }

    public void setCancelColor(int c) {
        tvCancel.setTextColor(c);
    }

    public void isHideConfirm(boolean isHide) {
        isHideCancelOrConfirm(tvConfirm, isHide);
    }

    public void isHideCancel(boolean isHide) {
        isHideCancelOrConfirm(tvCancel, isHide);
    }

    private void isHideCancelOrConfirm(TextView textView, boolean isHide) {
        if (isHide) {
            textView.setVisibility(View.GONE);

        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void isShowMenuView(boolean isShow) {
        if (isShow) {
            menuView.setVisibility(View.VISIBLE);

        } else {
            menuView.setVisibility(View.GONE);
        }
    }

    public void setMsgSize(int size) {
        tvTitle.setTextSize(size);
    }

    public void setConfirmTextSize(int size) {
        tvConfirm.setTextSize(size);
    }

    /**
     * 设置确定按钮的宽度为wrap_content
     */
    public void setConfirmTextLayoutParamsWidthWrap() {
        ViewGroup.LayoutParams params = tvConfirm.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvConfirm.setLayoutParams(params);
        }
    }

}

