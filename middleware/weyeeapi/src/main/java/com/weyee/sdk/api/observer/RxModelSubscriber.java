package com.weyee.sdk.api.observer;

import android.text.TextUtils;
import com.weyee.sdk.toast.ToastUtils;
import io.reactivex.disposables.Disposable;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
abstract class RxModelSubscriber<T> extends BaseModelObserver<T> {

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 完成后的回调
     */
    protected abstract void onCompleted();

    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!showToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.show(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        onCompleted();
    }
}
