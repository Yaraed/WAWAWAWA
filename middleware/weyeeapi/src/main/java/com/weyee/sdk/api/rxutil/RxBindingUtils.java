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

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding3.widget.RxAdapterView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.weyee.sdk.api.rxutil.subscribe.SimpleThrowableAction;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

/**
 * <p>RxBinding工具
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/24 0024
 */
public class RxBindingUtils {
    private RxBindingUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //========================点击事件=============================//

    /**
     * 自定义控件监听
     *
     * @param v 监听控件
     * @return
     */
    public static Observable<Object> setViewClicks(View v) {
        return setViewClicks(v, 1, TimeUnit.SECONDS);
    }

    /**
     * 自定义控件监听
     *
     * @param v        监听控件
     * @param duration 点击时间间隔
     * @param unit     时间间隔单位
     * @return
     */
    public static Observable<Object> setViewClicks(View v, long duration, TimeUnit unit) {
        return RxView.clicks(v)
                .compose(RxOperationUtils.<Object>_throttleFirst(duration, unit))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 简单的控件点击监听
     *
     * @param v       监听控件
     * @param action1 监听事件
     * @return
     */
    public static Disposable setViewClicks(View v, Consumer<Object> action1) {
        return setViewClicks(v, 1, TimeUnit.SECONDS, action1, new SimpleThrowableAction());
    }

    /**
     * 简单的控件点击监听
     *
     * @param v        监听控件
     * @param duration 点击时间间隔
     * @param unit     时间间隔单位
     * @param action1  监听事件
     * @return
     */
    public static Disposable setViewClicks(View v, long duration, TimeUnit unit, Consumer<Object> action1) {
        return setViewClicks(v, duration, unit, action1, new SimpleThrowableAction());
    }

    /**
     * 简单的控件点击监听
     *
     * @param v           监听控件
     * @param duration    点击时间间隔
     * @param unit        时间间隔单位
     * @param action1     监听事件
     * @param errorAction 出错的事件
     * @return
     */
    public static Disposable setViewClicks(View v, long duration, TimeUnit unit, Consumer<Object> action1, Consumer<Throwable> errorAction) {
        return RxView.clicks(v)
                .compose(RxOperationUtils._throttleFirst(duration, unit))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, errorAction);
    }

    /**
     * AdapterView控件点击监听
     *
     * @param view    监听控件
     * @param action1 条目点击监听事件
     */
    public static Disposable setItemClicks(AdapterView<?> view, Consumer<AdapterViewItemClickEvent> action1) {
        return setItemClicks(view, 1, TimeUnit.SECONDS, action1, new SimpleThrowableAction());
    }

    /**
     * AdapterView控件点击监听
     *
     * @param view        监听控件
     * @param action1     条目点击监听事件
     * @param errorAction 出错的事件
     */
    public static Disposable setItemClicks(AdapterView<?> view, long duration, TimeUnit unit, Consumer<AdapterViewItemClickEvent> action1, Consumer<Throwable> errorAction) {
        return RxAdapterView.itemClickEvents(view)
                .compose(RxOperationUtils.<AdapterViewItemClickEvent>_throttleFirst(duration, unit))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, errorAction);
    }

    //========================变化事件=============================//

    /**
     * 简单的文字变化监听
     *
     * @param textView 监听控件
     * @return
     */
    public static Observable<CharSequence> textChanges(TextView textView) {
        return RxTextView.textChanges(textView);
    }

    /**
     * 简单的文字变化监听
     *
     * @param textView 监听控件
     * @param timeout  响应的间隔
     * @param unit     时间间隔单位
     * @return
     */
    public static Observable<CharSequence> textChanges(TextView textView, long timeout, TimeUnit unit) {
        return RxTextView.textChanges(textView)
                .compose(RxOperationUtils.<CharSequence>_debounce(timeout, unit))
                .skip(1) //跳过第1次数据发射 = 初始输入框的空字符状态
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 简单的文字变化监听
     *
     * @param textView 监听控件
     * @param timeout  响应的间隔
     * @param unit     时间间隔单位
     * @param action1  响应的动作
     * @return
     */
    public static Disposable textChanges(TextView textView, long timeout, TimeUnit unit, Consumer<CharSequence> action1) {
        return textChanges(textView, timeout, unit).subscribe(action1, new SimpleThrowableAction());
    }
}
