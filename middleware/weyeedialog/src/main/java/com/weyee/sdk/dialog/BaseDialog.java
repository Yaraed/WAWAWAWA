package com.weyee.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.ScreenUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO
    }

    /**
     * 设置dialog的位置的宽高
     */
    protected void setViewLocation() {
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
        window.setAttributes(params);
    }
}
