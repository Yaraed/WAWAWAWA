package com.weyee.sdk.api.core;

import com.weyee.sdk.api.core.util.HttpLogFormatHelper;
import com.weyee.sdk.log.LogUtils;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <p>Okhttp网络日志拦截器</p>
 *
 * @author: mcgrady
 * @date: 2018/7/17
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger {

    public static final String TAG = HttpLogger.class.getSimpleName();

    private StringBuilder strBuilder = new StringBuilder();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            strBuilder.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = HttpLogFormatHelper.formatJson(HttpLogFormatHelper.decodeUnicode(message));
        }
        strBuilder.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtils.d(strBuilder.toString());
        }
    }
}
