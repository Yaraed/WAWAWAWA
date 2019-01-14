package com.weyee.posres.callback;

/**
 * <p>两参回调
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/26 0026
 */
public interface Callback2<T, R> {
    void call(T t, R r);
}
