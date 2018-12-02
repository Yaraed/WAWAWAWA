package com.weyee.sdk.event;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public class EventBus implements IEventBus {
    private EventBus() {

    }

    public static void register(Object subscriber) {
        org.greenrobot.eventbus.EventBus.getDefault().register(subscriber);
    }

    public static boolean isRegistered(Object subscriber) {
        return org.greenrobot.eventbus.EventBus.getDefault().isRegistered(subscriber);
    }

    public static void unregister(Object subscriber) {
        org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
    }

    public static void post(Object event) {
        org.greenrobot.eventbus.EventBus.getDefault().post(event);
    }

    public static void postSticky(Object event) {
        org.greenrobot.eventbus.EventBus.getDefault().postSticky(event);
    }
}
