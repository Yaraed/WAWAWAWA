package com.weyee.sdk.api.observer;

import io.reactivex.disposables.Disposable;

/**
 * <p>
 * 定义请求结果处理接口
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
interface ISubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 错误回调
     *
     * @param errorMsg 错误信息
     */
    void doOnError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t 泛型
     */
    void doOnNext(T t);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
