package com.weyee.sdk.api.core.exception;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * <p>异常归类处理。</p>
 * <p>errorMsg 尽量简洁明了提示错误信息，并指导用户处理方案。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class ExceptionHandle {

    /**
     * 处理全局异常
     *
     * @param throwable
     */
    public static ApiException handleException(Throwable throwable) {
        int errorCode = ErrorCode.UNKNOWN;
        String errorMsg = "未知异常，请稍后重试!";

        if (throwable instanceof JsonParseException
                || throwable instanceof JSONException) {
            errorCode = ErrorCode.PARSE;
            errorMsg = "解析数据异常，请检查数据！";

        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            errorCode = ErrorCode.NETWORK;
            errorMsg = "网络连接异常，请检查网络！";
        }

        ApiException exception = new ApiException(String.valueOf(errorCode), errorMsg + "  " + throwable.getMessage());
        return exception;
    }

}
