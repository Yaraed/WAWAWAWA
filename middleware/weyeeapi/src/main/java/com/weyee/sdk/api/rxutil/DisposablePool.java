/*
 * Copyright (c) 2018 liu-feng
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weyee.sdk.api.rxutil;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>RxJava订阅池
 * <p>
 * 为什么注释掉呢：
 * ①单个订阅管理不是很方便，绑定和解绑会创建变量，不利于维护
 * ②替代方案有许多：在{@link BasePresenter}中已经维护了一套解绑机制，通过{@link AutoDispose}中通过绑定生命周期进行解绑，通过{@link RxLifecycle}进行生命周期解绑
 * <p>
 * 为什么不删除呢：
 * 可能我们在全局的生命周期中需要保存长时间的对象，而是需要在退出app或者退出登录才会去销毁对象，不过使用不是很频繁
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/21 0021
 */
@Deprecated
public class DisposablePool {
    private static DisposablePool sInstance;

    //===================================订阅管理=======================================//
    /**
     * RxJava订阅池，管理Subscribers订阅，防止内存泄漏
     */
    private ConcurrentHashMap<Object, CompositeDisposable> maps = new ConcurrentHashMap<>();

    private DisposablePool() {

    }

    /**
     * 获取订阅池
     *
     * @return
     */
    public static DisposablePool get() {
        if (sInstance == null) {
            synchronized (DisposablePool.class) {
                if (sInstance == null) {
                    sInstance = new DisposablePool();
                }
            }
        }
        return sInstance;
    }


    /**
     * 根据tagName管理订阅【注册订阅信息】
     *
     * @param tagName    标志
     * @param disposable 订阅信息
     */
    public Disposable add(@NonNull Object tagName, Disposable disposable) {
        /* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 根据tagName管理订阅【注册订阅信息】
     *
     * @param disposable 订阅信息
     * @param tagName    标志
     */
    public Disposable add(Disposable disposable, @NonNull Object tagName) {
        /* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 取消订阅【取消标志内所有订阅信息】
     *
     * @param tagName 标志
     */
    public void remove(@NonNull Object tagName) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.dispose(); //取消订阅
            maps.remove(tagName);
        }
    }

    /**
     * 取消订阅【单个订阅取消】
     *
     * @param tagName    标志
     * @param disposable 订阅信息
     */
    public void remove(@NonNull Object tagName, Disposable disposable) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable);
            if (compositeDisposable.size() == 0) {
                maps.remove(tagName);
            }
        }
    }

    /**
     * 取消所有订阅
     */
    public void removeAll() {
        Iterator<Map.Entry<Object, CompositeDisposable>> it = maps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = it.next();
            CompositeDisposable compositeDisposable = entry.getValue();
            if (compositeDisposable != null) {
                compositeDisposable.dispose(); //取消订阅
                it.remove();
            }
        }
        maps.clear();
    }


    /**
     * 取消订阅
     *
     * @param disposable 订阅信息
     */
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
