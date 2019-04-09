package com.weyee.sdk.event.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * @author wuqi by 2019/4/2.
 */
final class SafeCastObserver<T> implements Observer<T> {

    @NonNull
    private final Observer<T> observer;

    SafeCastObserver(@NonNull Observer<T> observer) {
        this.observer = observer;
    }

    @Override
    public void onChanged(@Nullable T t) {
        try {
            observer.onChanged(t);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}