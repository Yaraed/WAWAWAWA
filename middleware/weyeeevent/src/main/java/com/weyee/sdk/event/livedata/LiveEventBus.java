package com.weyee.sdk.event.livedata;

import com.weyee.sdk.event.IEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuqi by 2019/4/2.
 */
final class LiveEventBus {
    private final Map<String, BusMutableLiveData<IEvent>> bus;

    private LiveEventBus() {
        bus = new HashMap<>();
    }

    static class Holder {
        static final LiveEventBus BUS = new LiveEventBus();
    }

    public static LiveEventBus get() {
        return Holder.BUS;
    }

    public Map<String, BusMutableLiveData<IEvent>> getBus() {
        return bus;
    }

    public synchronized <T extends IEvent> Observable<T> with(String key) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<>(key));
        }
        return (Observable<T>) bus.get(key);
    }

}
