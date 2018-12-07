package com.weyee.sdk.api.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * <p>
 * 请求拦截器  统一添加请求头使用
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class AddHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        request.addHeader("Accept", "application/json;");
        request.addHeader("Connection", "keep-alive");
        return chain.proceed(request.build());
    }
}
