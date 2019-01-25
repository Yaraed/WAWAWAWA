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

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

import java.util.concurrent.TimeUnit;

/**
 * <p>Rx常用操作符集合 【使用compose操作符】
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/21 0021
 */
public class RxOperationUtils {
    private RxOperationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 【定期发射】， 用于解决快速点击。只取一定时间间隔发射的第一个数据<br>
     * 使用compose操作符
     *
     * @param duration 间隔时间
     * @param unit     时间的单位
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _throttleFirst(final long duration, final TimeUnit unit) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.throttleFirst(duration, unit);
            }
        };
    }

    /**
     * 【超时发射】，用于像TextWatcher这种频繁变化的事件。只取两次数据的发射间隔大于指定时间的数据<br>
     * <p>【发射数据时，如果两次数据的发射间隔小于指定时间，就会丢弃前一次的数据,直到指定时间内都没有新数据发射时才进行发射】</p>
     *
     * @param timeout 间隔时间
     * @param unit    时间的单位
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _debounce(final long timeout, final TimeUnit unit) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.debounce(timeout, unit);
            }
        };
    }
}
