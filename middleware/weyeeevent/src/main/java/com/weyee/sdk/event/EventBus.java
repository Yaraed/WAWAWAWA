package com.weyee.sdk.event;

/**
 * <p> 事件处理
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public class EventBus implements IEventBus {
    private EventBus() {

    }

    /**
     * 注册事件监听
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!isRegistered(subscriber)) {
            org.greenrobot.eventbus.EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 是否注册监听
     *
     * @param subscriber
     * @return
     */
    private static boolean isRegistered(Object subscriber) {
        return org.greenrobot.eventbus.EventBus.getDefault().isRegistered(subscriber);
    }

    /**
     * 解绑事件
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public static void post(IEvent event) {
        org.greenrobot.eventbus.EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    public static void postSticky(IEvent event) {
        org.greenrobot.eventbus.EventBus.getDefault().postSticky(event);
    }
}
