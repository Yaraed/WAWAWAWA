package com.weyee.sdk.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p> 日志打印类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/11/30 0030
 */
public final class LogUtils {
    /**
     * 默认的日志记录为Logcat
     */
    private static ILogger sILogger = new Logger();

    private LogUtils() {

    }

    public static void init() {
        //sILogger = new Timber();
        sILogger.addAdapter(null);
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        sILogger.d(message, args);
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        sILogger.e(message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        sILogger.e(throwable, message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        sILogger.w(message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        sILogger.i(message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        sILogger.v(message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        sILogger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     *
     * @param json
     */
    public static void json(@Nullable String json) {
        sILogger.json(json);
    }

    /**
     * Formats the given xml content and print it
     *
     * @param xml
     */
    public static void xml(@Nullable String xml) {
        sILogger.xml(xml);
    }

    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        sILogger.log(priority, tag, message, throwable);
    }

    public static void clearLogAdapters() {
        sILogger.clearLogAdapters();
    }
}
