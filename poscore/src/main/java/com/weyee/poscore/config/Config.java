package com.weyee.poscore.config;

import android.app.Application;
import com.weyee.posres.AutoSizeConfig;
import com.weyee.sdk.api.RxHttpUtils;
import com.weyee.sdk.log.LogUtils;
import com.weyee.sdk.toast.ToastUtils;

/**
 * <p>全局配置，默认配置
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
public class Config {
    public static void init(Application application) {
        AutoSizeConfig.init();
        ToastUtils.init(application);
        LogUtils.init();
        RxHttpUtils.getInstance().config().setBaseUrl("https://api.douban.com/");
    }
}
