package com.weyee.sdk.api.interceptor;

import android.annotation.SuppressLint;
import com.weyee.sdk.util.Tools;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * <p>
 *
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
@SuppressLint("MissingPermission")
public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        boolean connected = Tools.isConnected();
        if (connected) {
            //如果有网络，缓存60s
            Response response = chain.proceed(request);
            int maxTime = 60;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);
    }
}
