package com.weyee.sdk.event.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.concurrent.TimeUnit;

/**
 * @author wuqi by 2019/4/2.
 */
public interface Observable<T> {
    void setValue(T value);

    void postValue(T value);

    void postValueDelay(T value, long delay);

    void postValueDelay(T value, long delay, TimeUnit unit);

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer);

    void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer);

    void observeForever(@NonNull Observer<? super T> observer);

    void observeStickyForever(@NonNull Observer<T> observer);

    void removeObserver(@NonNull Observer<? super T> observer);
}
