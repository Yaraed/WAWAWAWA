package com.weyee.sdk.api.core.exception;

/**
 * <p>一句话描述。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class ApiException extends Exception implements ApiExceptionAble {
    private int code;

    public ApiException(String codeStr, String message) {
        super(message);

        try {
            code = Integer.parseInt(codeStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
