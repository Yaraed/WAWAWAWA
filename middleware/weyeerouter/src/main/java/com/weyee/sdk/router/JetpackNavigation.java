package com.weyee.sdk.router;

import android.content.Context;

/**
 * @author wuqi by 2019/4/3.
 */
public class JetpackNavigation extends Navigation {

    private static final String MODULE_NAME = Path.Jetpack;

    public JetpackNavigation(Context context) {
        super(context);
    }

    /**
     * 配置Module
     */
    @Override
    protected String getModuleName() {
        return MODULE_NAME;
    }

    public void toJetpackActivity() {
        startActivity("Jetpack");
    }
}
