package com.weyee.sdk.api.core.exception;

/**
 * <p>api 未配置异常。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class ApiConfigException extends NullPointerException implements ApiExceptionAble{

    public ApiConfigException() {
        super("API配置异常，请初始化时设置 ApiConfig 相关信息");
    }

    @Override
    public int getCode() {
        return ErrorCode.NO_CONFIG_API;
    }
}
