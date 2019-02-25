package com.weyee.sdk.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Printer;

/**
 * <p> 自定义log的接口类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
interface ILogger {
    void addAdapter(@Nullable LogAdapter adapter);

    Printer t(@Nullable String tag);

    void d(@NonNull String message, @Nullable Object... args);

    void d(@Nullable Object object);

    void e(@NonNull String message, @Nullable Object... args);

    void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void w(@NonNull String message, @Nullable Object... args);

    void i(@NonNull String message, @Nullable Object... args);

    void v(@NonNull String message, @Nullable Object... args);

    void wtf(@NonNull String message, @Nullable Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(@Nullable String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(@Nullable String xml);

    void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void clearLogAdapters();
}
