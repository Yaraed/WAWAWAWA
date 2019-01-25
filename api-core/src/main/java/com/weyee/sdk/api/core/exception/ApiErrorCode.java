package com.weyee.sdk.api.core.exception;

/**
 * <p>接口业务定义的错误码。</p>
 *
 * @author moguangjian
 * @date 2018/6/26
 */
public class ApiErrorCode extends ErrorCode {

    /**
     * 版本过低
     */
    public static final int LOW_VERSION = -999999;
    /**
     * 服务过期
     */
    public static final int SERVICE_EXPIRE = 999993;
    /**
     * 账号异常
     */
    public static final int ACCOUNT_ERROR = 401;
    /**
     * 时间异常
     */
    public static final int TIME_ERROR = 999998;
}
