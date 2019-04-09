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

package com.weyee.sdk.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Printer;

/**
 * @author wuqi by 2019/2/25.
 */
class Logger implements ILogger {
    @Override
    public void addAdapter(@Nullable LogAdapter adapter) {
        //        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                // (Optional) Whether to show thread info or not. Default true
//                .showThreadInfo(true)
//                // (Optional) How many method line to show. Default 2
//                .methodCount(2)
//                // (Optional) Hides internal method calls up to offset. Default 5
//                .methodOffset(0)
//                // (Optional) Changes the log strategy to print out. Default LogCat
//                //.logStrategy(customLog)
//                // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .tag("Letion").build();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return Environment.isDebug();
            }
        });
    }

    @Override
    public Printer t(@Nullable String tag) {
        return com.orhanobut.logger.Logger.t(tag);
    }

    @Override
    public void d(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.d(message, args);
    }

    @Override
    public void d(@Nullable Object object) {
        com.orhanobut.logger.Logger.d(object);
    }

    @Override
    public void e(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.e(message, args);
    }

    @Override
    public void e(@NonNull Throwable throwable) {
        com.orhanobut.logger.Logger.e(throwable,"ERROR");
    }

    @Override
    public void e(@NonNull Throwable throwable,@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.e(throwable, message, args);
    }

    @Override
    public void w(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.w(message, args);
    }

    @Override
    public void i(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.i(message, args);
    }

    @Override
    public void v(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.v(message, args);
    }

    @Override
    public void wtf(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.wtf(message, args);
    }

    @Override
    public void json(@Nullable String json) {
        com.orhanobut.logger.Logger.json(json);
    }

    @Override
    public void xml(@Nullable String xml) {
        com.orhanobut.logger.Logger.xml(xml);
    }

    @Override
    public void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        com.orhanobut.logger.Logger.log(priority, tag, message, throwable);
    }

    @Override
    public void clearLogAdapters() {
        com.orhanobut.logger.Logger.clearLogAdapters();
    }
}
