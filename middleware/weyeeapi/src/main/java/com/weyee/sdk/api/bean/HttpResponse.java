package com.weyee.sdk.api.bean;

/**
 * <p>
 * 返回数据基类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class HttpResponse<T> {

    private int status;
    /**
     * errorno : 0
     * errormsg : success
     */
    private ErrorEntity error;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ErrorEntity getError() {
        return error;
    }

    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class ErrorEntity {
        private int errorno;
        private String errormsg;

        public int getErrorno() {
            return errorno;
        }

        public void setErrorno(int errorno) {
            this.errorno = errorno;
        }

        public String getErrormsg() {
            return errormsg;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }
    }
}
