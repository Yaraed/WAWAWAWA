package com.weyee.sdk.api.observer;

import com.weyee.sdk.api.observer.listener.ProgressAble;
import io.reactivex.disposables.Disposable;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public abstract class ProgressModelSubscriber<T> extends RxModelSubscriber<T> {

    private ProgressAble progressAble;

    public ProgressModelSubscriber() {
    }

    public ProgressModelSubscriber(ProgressAble progressAble) {
        this.progressAble = progressAble;
    }

    @Override
    public void doOnSubscribe(Disposable d) {
        super.doOnSubscribe(d);
        if (progressAble != null) {
            progressAble.showProgress();
        }
    }

    @Override
    public void doOnCompleted() {
        super.doOnCompleted();
        if (progressAble != null) {
            progressAble.hideProgress();
        }
    }
}
