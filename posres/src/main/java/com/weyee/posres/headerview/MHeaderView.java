/*
 * Copyright (c) 2018 liu-feng
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weyee.posres.headerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.blankj.utilcode.util.SizeUtils;
import com.weyee.posres.R;

/**
 * 头部标题
 */
public class MHeaderView extends Toolbar implements MHeaderViewAble {

    private TextView tvMenuLeft;
    private TextView tvLeftClose;
    private TextView tvTitle;
    private TextView tvMenuRightThree;
    private TextView tvMenuRightTwo;
    private TextView tvMenuRightOne;
    private View bottomLine;
    private View newMsgView;

    private int titleColor;
    private int menuLeftOneTextColor;
    private int menuLeftOneIcon;
    private int menuRightOneTextColor;
    private int menuRightOneIcon;
    private int menuRightTwoTextColor;
    private int menuRightTwoIcon;
    private int menuRightThreeTextColor;
    private int menuRightThreeIcon;
    private int lineColor;
    private int menuLeftCloseIcon;

    public MHeaderView(Context context) {
        this(context, null, 0);
    }

    public MHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MHeaderView);

            titleColor = typedArray.getColor(R.styleable.MHeaderView_header_title_color, Color.WHITE);
            lineColor = typedArray.getColor(R.styleable.MHeaderView_header_line, Color.TRANSPARENT);

            menuLeftCloseIcon = typedArray.getResourceId(R.styleable
                    .MHeaderView_header_menu_left_close_icon, 0);

            menuLeftOneIcon = typedArray.getResourceId(R.styleable
                    .MHeaderView_header_menu_left_one_icon, android.R.drawable.ic_menu_revert);
            menuLeftOneTextColor = typedArray.getColor(R.styleable
                    .MHeaderView_header_menu_left_one_text_color, Color.WHITE);

            menuRightOneIcon = typedArray.getResourceId(R.styleable
                    .MHeaderView_header_menu_right_one_icon, 0);
            menuRightOneTextColor = typedArray.getColor(R.styleable
                    .MHeaderView_header_menu_right_one_text_color, Color.WHITE);

            menuRightTwoIcon = typedArray.getResourceId(R.styleable
                    .MHeaderView_header_menu_right_two_icon, 0);
            menuRightTwoTextColor = typedArray.getColor(R.styleable
                    .MHeaderView_header_menu_right_two_text_color, Color.WHITE);

            menuRightThreeIcon = typedArray.getResourceId(R.styleable
                    .MHeaderView_header_menu_right_three_icon, 0);
            menuRightThreeTextColor = typedArray.getColor(R.styleable
                    .MHeaderView_header_menu_right_three_text_color, Color.WHITE);

            typedArray.recycle();
        }
        LayoutInflater.from(getContext()).inflate(R.layout.m_view_header, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = SizeUtils.dp2px(0.5f);

        tvTitle = findViewById(R.id.tvTitle);

        tvLeftClose = findViewById(R.id.tvLeftClose);
        tvMenuLeft = findViewById(R.id.tvMenuLeft);
        tvMenuRightOne = findViewById(R.id.tvMenuRightOne);
        tvMenuRightTwo = findViewById(R.id.tvMenuRightTwo);
        tvMenuRightThree = findViewById(R.id.tvMenuRightThree);
        bottomLine = findViewById(R.id.bottomLine);
        newMsgView = findViewById(R.id.newMsgView);


        setTitleTextColor(titleColor);
        setBottomLineColor(lineColor);

        setMenuLeftCloseIcon(menuLeftCloseIcon);

        setMenuLeftBackIcon(menuLeftOneIcon);
        setMenuLeftTextColor(menuLeftOneTextColor);

        setMenuRightOneIcon(menuRightOneIcon);
        setMenuRightOneTextColor(menuRightOneTextColor);

        setMenuRightTwoIcon(menuRightTwoIcon);
        setMenuRightTwoTextColor(menuRightTwoTextColor);

        setMenuRightTwoIcon(menuRightThreeIcon);
        setMenuRightTwoTextColor(menuRightThreeTextColor);
    }

    @Override
    public void setOnClickLeftMenuBackListener(OnClickListener onClickListener) {
        tvMenuLeft.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnClickRightMenuOneListener(OnClickListener onClickListener) {
        tvMenuRightOne.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnClickRightMenuTwoListener(OnClickListener onClickListener) {
        tvMenuRightTwo.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnClickRightMenuThreeListener(OnClickListener onClickListener) {
        tvMenuRightThree.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnClickLeftMenuCloseListener(OnClickListener onClickListener) {
        tvLeftClose.setOnClickListener(onClickListener);
    }

    @Override
    public void isShowMenuLeftBackView(boolean isShow) {
        isShowView(tvMenuLeft, isShow);
    }

    @Override
    public void isShowMenuLeftCloseView(boolean isShow) {
        isShowView(tvLeftClose, isShow);
    }

    @Override
    public void isShowMenuRightOneView(boolean isShow) {
        isShowView(tvMenuRightOne, isShow);
    }

    @Override
    public void isShowMenuRightTwoView(boolean isShow) {
        isShowView(tvMenuRightTwo, isShow);
    }

    @Override
    public void isShowMenuRightThreeView(boolean isShow) {
        isShowView(tvMenuRightThree, isShow);
    }

    @Override
    public void isShowLine(boolean isShow) {
        if (isShow) {
            bottomLine.setVisibility(VISIBLE);
        } else {
            bottomLine.setVisibility(GONE);
        }
    }

    @Override
    public TextView getMenuLeftBackView() {
        return tvMenuLeft;
    }

    @Override
    public TextView getMenuLeftCloseView() {
        return tvLeftClose;
    }

    @Override
    public TextView getMenuRightOneView() {
        return tvMenuRightOne;
    }

    @Override
    public TextView getMenuRightTwoView() {
        return tvMenuRightTwo;
    }

    @Override
    public TextView getMenuRightThreeView() {
        return tvMenuRightThree;
    }

    @Override
    public TextView getTitleView() {
        return tvTitle;
    }

    @Override
    public void setBottomLineColor(@ColorInt int colorId) {
        bottomLine.setBackgroundColor(colorId);
    }

    @Override
    public void setTitleTextColor(int colorId) {
        tvTitle.setTextColor(colorId);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuLeftBackIcon(@DrawableRes int id) {
        if (id > 0) {
            tvMenuLeft.setCompoundDrawables(null, null, getDrawable(getContext(), id), null);
        } else {
            tvMenuLeft.setCompoundDrawables(null, null, null, null);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuLeftCloseIcon(@DrawableRes int id) {
        if (id > 0) {
            tvLeftClose.setCompoundDrawables(null, null, getDrawable(getContext(), id), null);
        } else {
            tvLeftClose.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void setMenuLeftTextColor(int colorId) {
        tvMenuLeft.setTextColor(colorId);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuRightOneIcon(@DrawableRes int id) {
        if (id > 0) {
            tvMenuRightOne.setCompoundDrawables(getDrawable(getContext(), id), null, null, null);
        } else {
            tvMenuRightOne.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void setMenuRightOneTextColor(int colorId) {
        tvMenuRightOne.setTextColor(colorId);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuRightTwoIcon(@DrawableRes int id) {
        if (id > 0) {
            tvMenuRightTwo.setCompoundDrawables(getDrawable(getContext(), id), null, null, null);
        } else {
            tvMenuRightTwo.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void setMenuRightTwoTextColor(int colorId) {
        tvMenuRightTwo.setTextColor(colorId);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuRightThreeIcon(@DrawableRes int id) {
        if (id > 0) {
            tvMenuRightThree.setCompoundDrawables(getDrawable(getContext(), id), null, null, null);
        } else {
            tvMenuRightThree.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void setMenuRightThreeTextColor(int colorId) {
        tvMenuRightThree.setTextColor(colorId);
    }

    @Override
    public void setMenuLeftBackView(@DrawableRes int id, String title) {
        tvMenuLeft.setText(title);
        setMenuLeftBackIcon(id);

    }

    @Override
    public void setMenuRightViewOne(@DrawableRes int id, String title) {
        tvMenuRightOne.setText(title);
        setMenuRightOneIcon(id);

    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuRightViewTwo(@DrawableRes int id, String title) {
        tvMenuRightTwo.setText(title);
        if (id > 0) {
            tvMenuRightTwo.setCompoundDrawables(getDrawable(getContext(), id), null, null, null);
        } else {
            tvMenuRightTwo.setCompoundDrawables(null, null, null, null);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void setMenuRightViewThree(int id, String title) {
        tvMenuRightThree.setText(title);
        if (id > 0) {
            tvMenuRightThree.setCompoundDrawables(getDrawable(getContext(), id), null, null, null);
        } else {
            tvMenuRightThree.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 是否显示信息的点
     *
     * @param isShow
     */
    @Override
    public void isShowNewsHint(boolean isShow) {
        if (isShow) {
            newMsgView.setVisibility(VISIBLE);

        } else {
            newMsgView.setVisibility(GONE);
        }
    }

    private void isShowView(View view, boolean isShow) {
        if (isShow) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
    }

    private static Drawable getDrawable(Context context, int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }
}
