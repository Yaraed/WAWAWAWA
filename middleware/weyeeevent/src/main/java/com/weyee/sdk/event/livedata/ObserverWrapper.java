package com.weyee.sdk.event.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * @author wuqi by 2019/4/2.
 */
final class ObserverWrapper<T> implements Observer<T> {

    @NonNull
    private final Observer<T> observer;

    ObserverWrapper(@NonNull Observer<T> observer) {
        this.observer = observer;
    }

    @Override
    public void onChanged(@Nullable T t) {
        if (isCallOnObserve()) {
            return;
        }
        try {
            observer.onChanged(t);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private boolean isCallOnObserve() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement element : stackTrace) {
                if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
                        "observeForever".equals(element.getMethodName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
