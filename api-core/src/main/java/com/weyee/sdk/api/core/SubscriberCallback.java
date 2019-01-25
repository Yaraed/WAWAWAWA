package com.weyee.sdk.api.core;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/7/11
 */

public abstract class SubscriberCallback<T> {

    /**
     * 成功
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * 最终错误回调
     *
     * @param errorCode
     * @param errorMsg
     */
    public void onFailure(int errorCode, String errorMsg) {

    }

    /**
     * 请求完成
     */
    public void onCompleted() {
    }



}
