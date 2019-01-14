package com.weyee.posres.callback;

/**
 * <p>两参回调
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/26 0026
 */
public interface Callback<T> {
    void call(T... t);
}
