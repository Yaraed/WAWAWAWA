package com.weyee.sdk.toast;

import android.app.Application;

/**
 * <p>
 * 不需要通知栏权限的 Toast
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/4 0004
 */
final class YToast extends XToast {

    // 吐司弹窗显示辅助类
    private final ToastHelper mToastHelper;

    YToast(Application application) {
        super(application);
        mToastHelper = new ToastHelper(this, application);
    }

    @Override
    public void show() {
        // 显示吐司
        mToastHelper.show();
    }

    @Override
    public void cancel() {
        // 取消显示
        mToastHelper.cancel();
    }
}
