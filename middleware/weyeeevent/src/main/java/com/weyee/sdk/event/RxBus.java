/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.weyee.sdk.event;

import androidx.annotation.NonNull;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 事件处理 rx bus待实现
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
final class RxBus implements IEventBus {

    private final FlowableProcessor<Object> mBus;
    // 保存sticky message
    private final Map<Class, List<TagEvent>> stickyEventsMap = new ConcurrentHashMap<>();
    // 事件
    private final Map<Object, List<Disposable>> disposablesMap = new ConcurrentHashMap<>();

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    /**
     * 解绑事件
     *
     * @param subscriber
     */
    @Override
    public void unregister(Object subscriber) {
        removeDisposables(subscriber);
    }

    @Override
    public void post(@NonNull IEvent event) {
        post(event,this.getClass().getSimpleName());
    }

    @Override
    public void postSticky(@NonNull IEvent event) {
        postSticky(event,this.getClass().getSimpleName());
    }

    /**
     * 发送事件
     *
     * @param event
     */
    @Override
    public void post(@NonNull IEvent event, String tag) {
        Utils.requireNonNull(event);
        TagEvent stickyEvent = new TagEvent(event, tag);
        mBus.onNext(stickyEvent);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    @Override
    public void postSticky(@NonNull IEvent event, String tag) {
        Utils.requireNonNull(event);
        TagEvent stickyEvent = new TagEvent(event, tag);
        Class eventType = stickyEvent.getEventType();
        synchronized (stickyEventsMap) {
            List<TagEvent> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) {
                stickyEvents = new ArrayList<>();
                stickyEvents.add(stickyEvent);
                stickyEventsMap.put(eventType, stickyEvents);
            } else {
                int indexOf = stickyEvents.indexOf(stickyEvent);
                if (indexOf == -1) {// 不存在直接插入
                    stickyEvents.add(stickyEvent);
                } else {// 存在则覆盖
                    stickyEvents.set(indexOf, stickyEvent);
                }
            }
        }
    }

    @Override
    public <T> void subscribe(@NonNull Object subscriber, String tag, Scheduler scheduler, Callback<T> callback) {
        subscribe(subscriber,tag,false,scheduler,callback);
    }

    @Override
    public <T> void subscribeSticky(@NonNull Object subscriber, String tag, Scheduler scheduler, Callback<T> callback) {
        subscribe(subscriber,tag,true,scheduler,callback);
    }

    private <T> void subscribe(final Object subscriber,
                               final String tag,
                               final boolean isSticky,
                               final Scheduler scheduler,
                               final Callback<T> callback) {
        Utils.requireNonNull(subscriber, tag, callback);

        final Class<T> typeClass = Utils.getTypeClassFromParadigm(callback);

        final Consumer<T> onNext = callback::onEvent;

        if (isSticky) {
            final TagEvent stickyEvent = findStickyEvent(typeClass, tag);
            if (stickyEvent != null) {
                Flowable<T> stickyFlowable = Flowable.create(emitter -> emitter.onNext(typeClass.cast(stickyEvent.mEvent)), BackpressureStrategy.LATEST);
                if (scheduler != null) {
                    stickyFlowable = stickyFlowable.observeOn(scheduler);
                }
                Disposable stickyDisposable = subscribe(stickyFlowable, onNext);
                addDisposable(subscriber, stickyDisposable);
            } else {
                System.out.println("sticky event is empty.");
            }
        }
        Disposable disposable = subscribe(toFlowable(typeClass, tag, scheduler), onNext);
        addDisposable(subscriber, disposable);
    }

    private TagEvent findStickyEvent(final Class eventType, final String tag) {
        synchronized (stickyEventsMap) {
            List<TagEvent> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) return null;
            int size = stickyEvents.size();
            TagEvent res = null;
            for (int i = size - 1; i >= 0; --i) {
                TagEvent stickyEvent = stickyEvents.get(i);
                if (stickyEvent.isSameType(eventType, tag)) {
                    res = stickyEvents.get(i);
                    break;
                }
            }
            return res;
        }
    }

    private <T> Flowable<T> toFlowable(final Class<T> eventType,
                                       final String tag,
                                       final Scheduler scheduler) {
        Flowable<T> flowable = mBus.ofType(TagEvent.class)
                .filter(tagMessage -> tagMessage.isSameType(eventType, tag))
                .map(tagMessage -> tagMessage.mEvent)
                .cast(eventType);
        if (scheduler != null) {
            return flowable.observeOn(scheduler);
        }
        return flowable;
    }

    private <T> Disposable subscribe(Flowable<T> flowable, Consumer<? super T> onNext) {
        LambdaSubscriber<T> ls = new LambdaSubscriber<>(onNext, Functions.ERROR_CONSUMER, Functions.EMPTY_ACTION,
                FlowableInternalHelper.RequestMax.INSTANCE);
        flowable.subscribe(ls);
        return ls;
    }

    private void addDisposable(Object subscriber, Disposable disposable) {
        synchronized (disposablesMap) {
            List<Disposable> list = disposablesMap.get(subscriber);
            if (list == null) {
                list = new ArrayList<>();
                list.add(disposable);
                disposablesMap.put(subscriber, list);
            } else {
                list.add(disposable);
            }
        }
    }

    private void removeDisposables(final Object subscriber) {
        synchronized (disposablesMap) {
            List<Disposable> disposables = disposablesMap.get(subscriber);
            if (disposables == null) return;
            for (Disposable disposable : disposables) {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
            disposables.clear();
            disposablesMap.remove(subscriber);
        }
    }


    static class Holder {
        static final RxBus BUS = new RxBus();
    }
}
