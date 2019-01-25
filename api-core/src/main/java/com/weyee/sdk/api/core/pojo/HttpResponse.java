package com.weyee.sdk.api.core.pojo;

/**
 * <p>接口返回的基础格式。</p>
 *
 * @author moguangjian
 * @date 2018/6/21
 */
public class HttpResponse<T> {

    private int status;
    private ErrorEntity error;
    private T data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public ErrorEntity getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public static class ErrorEntity {
        /**
         * errorno : 0
         * errormsg : success
         */

        private String errorno;
        private String errormsg;

        public void setErrorno(String errorno) {
            this.errorno = errorno;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }

        public String getErrorno() {
            return errorno;
        }

        public String getErrormsg() {
            return errormsg;
        }
    }
}
