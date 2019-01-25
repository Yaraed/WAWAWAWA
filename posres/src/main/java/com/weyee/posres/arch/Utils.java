/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weyee.posres.arch;

import android.content.Intent;

/**
 * Created by Chaojun Wang on 6/9/14.
 */
public class Utils {
    public static final String KEY_ACTIVITY_ANIM_STYLE = "key_activity_anim_style";

    /**
     * 右进右出
     */
    public static final int ANIM_STYLE_SLID_IN_RIGHT = 1;
    /**
     * 底部进入
     */
    public static final int ANIM_STYLE_SLID_IN_BOTTOM = 2;

    /**
     * 右进左出
     */
    public static final int ANIM_STYLE_SLID_IN_RIGHT_OUT_LEFT = 3;
    /**
     * 无动画
     */
    public static final int ANIM_STYLE_NONE = -1;

    private Utils() {
    }

    public static int getActivityAnimStyle(Intent intent) {
        return intent == null ? -1 : intent.getIntExtra(KEY_ACTIVITY_ANIM_STYLE, -1);
    }
}
