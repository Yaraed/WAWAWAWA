package com.weyee.sdk.api.core.exception;

/**
 * <p>错误码常量。</p>
 *
 * @author moguangjian
 * @date 2018/6/26
 */
public class ErrorCode {

    /**
     * 登录异常
     */
    public static final int LOGIN_ERROR = 401;

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORK = 1002;

    /**
     * api 未初始化。
     */
    public static final int INIT_API = 1003;
    /**
     * apiConfig 未配置。
     */
    public static final int NO_CONFIG_API = 1004;



}
