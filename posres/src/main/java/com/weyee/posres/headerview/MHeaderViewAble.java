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

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

/**
 * <p>统一的头部布局
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/21 0021
 */
public interface MHeaderViewAble {

    void setOnClickLeftMenuBackListener(View.OnClickListener onClickListener);
    void setOnClickRightMenuOneListener(View.OnClickListener onClickListener);
    void setOnClickRightMenuTwoListener(View.OnClickListener onClickListener);
    void setOnClickRightMenuThreeListener(View.OnClickListener onClickListener);
    void setOnClickLeftMenuCloseListener(View.OnClickListener onClickListener);

    void isShowMenuLeftBackView(boolean isShow);
    void isShowMenuLeftCloseView(boolean isShow);
    void isShowMenuRightOneView(boolean isShow);
    void isShowMenuRightTwoView(boolean isShow);
    void isShowMenuRightThreeView(boolean isShow);
    void isShowLine(boolean isShow);

    TextView getMenuLeftBackView();
    ImageView getMenuLeftCloseView();
    TextView getMenuRightOneView();
    TextView getMenuRightTwoView();
    TextView getMenuRightThreeView();
    TextView getTitleView();

    void setBottomLineColor(@ColorInt int colorId);

    void setTitleTextColor(int colorId);

    void setMenuLeftBackView(@DrawableRes int id, String title);
    void setMenuLeftBackIcon(@DrawableRes int id);
    void setMenuLeftCloseIcon(@DrawableRes int id);
    void setMenuLeftTextColor(int colorId);

    void setMenuRightOneIcon(@DrawableRes int id);
    void setMenuRightOneTextColor(int colorId);
    void setMenuRightTwoIcon(@DrawableRes int id);
    void setMenuRightTwoTextColor(int colorId);
    void setMenuRightThreeIcon(@DrawableRes int id);
    void setMenuRightThreeTextColor(int colorId);
    void setMenuRightViewOne(@DrawableRes int id, String title);
    void setMenuRightViewTwo(@DrawableRes int id, String title);
    void setMenuRightViewThree(@DrawableRes int id, String title);

    void setTitle(String title);

    /**
     * 是否显示新消息的点
     * @param isShow
     */
    void isShowNewsHint(boolean isShow);


}
