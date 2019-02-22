package com.weyee.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;

/**
 * <p>dialog 基类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/18 0018
 */
public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        this(context, R.style.QMUI_Dialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
