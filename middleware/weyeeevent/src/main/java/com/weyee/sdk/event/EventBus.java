package com.weyee.sdk.event;

import androidx.annotation.NonNull;

/**
 * <p> 事件处理
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
final class EventBus implements IEventBus{
    private EventBus() {

    }

    /**
     * 注册事件监听
     *
     * @param subscriber
     */
    @Override
    public void register(Object subscriber) {
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
    @Override
    public boolean isRegistered(Object subscriber) {
        return org.greenrobot.eventbus.EventBus.getDefault().isRegistered(subscriber);
    }

    /**
     * 解绑事件
     *
     * @param subscriber
     */
    @Override
    public void unregister(Object subscriber) {
        org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送事件
     *
     * @param event
     */
    @Override
    public void post(@NonNull IEvent event) {
        org.greenrobot.eventbus.EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    @Override
    public void postSticky(@NonNull IEvent event) {
        org.greenrobot.eventbus.EventBus.getDefault().postSticky(event);
    }

    @Override
    public void post(@NonNull IEvent event, String tag) {
        post(event);
    }

    @Override
    public void postSticky(@NonNull IEvent event, String tag) {
        postSticky(event);
    }

    static class Holder {
        static final EventBus BUS = new EventBus();
    }
}
