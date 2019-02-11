package com.weyee.sdk.event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Scheduler;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public interface IEventBus {
    default void register(Object subscriber) {

    }

    default boolean isRegistered(Object subscriber) {
        return false;
    }

    void unregister(Object subscriber);


    default void post(@NonNull IEvent event) {

    }

    default void postSticky(@NonNull IEvent event) {

    }

    default void post(@NonNull IEvent event, String tag) {
    }

    default void postSticky(@NonNull IEvent event, String tag) {
    }

    default <T> void subscribe(@NonNull final Object subscriber,
                               @Nullable final String tag,
                               @Nullable final Scheduler scheduler,
                               final Callback<T> callback) {
    }

    default <T> void subscribeSticky(@NonNull final Object subscriber,
                                     @Nullable final String tag,
                                     @Nullable final Scheduler scheduler,
                                     final Callback<T> callback) {
    }
}
