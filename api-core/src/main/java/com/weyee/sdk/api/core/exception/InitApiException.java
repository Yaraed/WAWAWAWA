package com.weyee.sdk.api.core.exception;

/**
 * <p>api 初始化异常。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class InitApiException extends NullPointerException implements ApiExceptionAble{

    public InitApiException() {
        super("请先调用初始化方法：ApiFactory.init()");
    }

    @Override
    public int getCode() {
        return ErrorCode.INIT_API;
    }
}
