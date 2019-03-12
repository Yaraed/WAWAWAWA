package com.weyee.sdk.api.interceptor;

import com.weyee.sdk.api.config.Config;
import com.weyee.sdk.log.LogUtils;
import com.weyee.sdk.util.sp.SpUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * <p>
 * 添加cookie
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet<String>) SpUtils.getDefault().getStringSet(Config.COOKIE, new HashSet<>());
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
                LogUtils.d("Adding Header Cookie--->: " + cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
