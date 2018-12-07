package com.weyee.sdk.api.interceptor;

import com.blankj.utilcode.util.SPUtils;
import com.weyee.sdk.api.config.Config;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * <p>
 * 接受服务端返回的cookie，并保存到本地
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class RecvCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            SPUtils.getInstance().put(Config.COOKIE, cookies);
        }

        return originalResponse;
    }
}