package com.weyee.sdk.log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.orhanobut.logger.Printer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p> 日志打印类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public final class Logger implements ILogger {
    private Logger() {

    }

    public static void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(false)
                // (Optional) How many method line to show. Default 2
                .methodCount(0)
                // (Optional) Hides internal method calls up to offset. Default 5
                .methodOffset(7)
                // (Optional) Changes the log strategy to print out. Default LogCat
                //.logStrategy(customLog)
                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("Letion").build();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return Environment.isDebug();
            }
        });
    }

    public static void addAdapter(@NonNull LogAdapter adapter) {
        com.orhanobut.logger.Logger.addLogAdapter(adapter);
    }

    public static Printer t(@Nullable String tag) {
        return com.orhanobut.logger.Logger.t(tag);
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.d(message, args);
    }

    public static void d(@Nullable Object object) {
        com.orhanobut.logger.Logger.d(object);
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.e(message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.e(throwable, message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.w(message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.i(message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.v(message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        com.orhanobut.logger.Logger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     *
     * @param json
     */
    public static void json(@Nullable String json) {
        com.orhanobut.logger.Logger.json(json);
    }

    /**
     * Formats the given xml content and print it
     *
     * @param xml
     */
    public static void xml(@Nullable String xml) {
        com.orhanobut.logger.Logger.xml(xml);
    }

    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        com.orhanobut.logger.Logger.log(priority, tag, message, throwable);
    }

    public static void clearLogAdapters() {
        com.orhanobut.logger.Logger.clearLogAdapters();
    }
}
