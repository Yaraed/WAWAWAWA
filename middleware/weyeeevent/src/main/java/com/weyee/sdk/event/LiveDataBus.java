package com.weyee.sdk.event;

import androidx.lifecycle.MutableLiveData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuqi by 2019/4/1.
 */
final class LiveDataBus implements IEventBus {

    // 事件
    private final Map<Object, MutableLiveData<IEvent>> bus = new ConcurrentHashMap<>();


    @Override
    public void unregister(Object subscriber) {
        bus.remove(subscriber);
    }

    @Override
    public <T extends IEvent> MutableLiveData<T> get(Object subscriber) {
        if (!bus.containsKey(subscriber)) {
            bus.put(subscriber, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(subscriber);
    }

    static class Holder {
        static final LiveDataBus BUS = new LiveDataBus();
    }

}
