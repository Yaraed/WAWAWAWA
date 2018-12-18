package com.weyee.sdk.api.core;

/**
 * <p>全局自定义api 错误请求提示。</p>
 *
 * @author moguangjian
 * @date 2018/6/26
 */
public interface ErrorHintAble {

    /**
     * api错误回调。
     *
     * @param code 错误状态码。
     * @param msg  错误信息。
     * @param exception 错误报的异常。
     */
    void onError(int code, String msg, Exception exception);

    /**
     * 是否使用默认提示框展示错误信息。
     * @return
     */
    boolean isShowDefaultHint();
}
