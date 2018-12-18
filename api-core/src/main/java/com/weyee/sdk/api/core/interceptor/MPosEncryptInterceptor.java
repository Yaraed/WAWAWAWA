package com.weyee.sdk.api.core.interceptor;

import com.weyee.sdk.api.core.ApiFactory;
import com.weyee.sdk.api.core.util.DeviceInfoHelper;

import java.util.Map;

/**
 * <p>mpos 端加密拦截器。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class MPosEncryptInterceptor extends WeYeeEncryptInterceptor{

    @Override
    protected void prepareParams(Map<String, Object> params) {
        params.put("access_token", "");
        params.put("vendor_user_id", "getUserId");
        params.put("app_ver", DeviceInfoHelper.getAppVersionName(ApiFactory.context));
        params.put("ver", "接口版本");
        params.put("device_info", DeviceInfoHelper.getDeviceInfo(ApiFactory.context, DeviceInfoHelper.APP_TYPE_MPOS));
        params.put("app_type", DeviceInfoHelper.APP_TYPE_MPOS);
        params.put("appid", "Fje9Lgnn1iSHpheE");

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String timeDiff = "0";
        params.put("timediff", timeDiff);
        params.put("timestamp", timestamp);

        if (!params.containsKey("pagesize")) {
            params.put("pagesize", "");
        }
        if (!params.containsKey("pageSize")) {
            params.put("pageSize", "");
        }
    }

    @Override
    protected String getAppKey() {
        return "j8WQA3fbXDowZdd7k9guPVKWJmo3rK9W";
    }
}
