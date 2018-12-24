package com.weyee.sdk.api.observer;

import com.weyee.sdk.api.bean.HttpResponse;
import com.weyee.sdk.api.dispose.DisposeManager;
import com.weyee.sdk.api.exception.ApiException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
abstract class BaseModelObserver<T> implements Observer<HttpResponse<T>>, IModelSubscriber<T> {

    /**
     * 是否隐藏toast
     *
     * @return boolean
     */
    protected boolean showToast() {
        return false;
    }

    /**
     * 标记网络请求的tag
     * tag下的一组或一个请求，用来处理一个页面的所以请求或者某个请求
     * 设置一个tag就行就可以取消当前页面所有请求或者某个请求了
     *
     * @return string
     */
    protected String setTag() {
        return null;
    }

    @Override
    public void onSubscribe(Disposable d) {
        DisposeManager.get().add(setTag(), d);
        doOnSubscribe(d);
    }

    @Override
    public void onNext(HttpResponse<T> baseData) {
        doOnNext(baseData.getData());
    }

    @Override
    public void onError(Throwable e) {
        doOnError(ApiException.handleException(e).getMessage());
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }
}
