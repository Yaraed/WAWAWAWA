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

    private LogUtils() {

    }

    private static class Holder {
        private static ILogger sILogger = new Logger();
        //Holder.sILogger = new Timber();
    }

    public static void init() {
        Holder.sILogger.addAdapter(null);
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        Holder.sILogger.d(message, args);
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        Holder.sILogger.e(message, args);
    }

    public static void e(@NonNull Throwable throwable) {
        Holder.sILogger.e(throwable);
    }

    public static void e(@NonNull Throwable throwable, @NonNull String message, @Nullable Object... args) {
        Holder.sILogger.e(throwable, message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        Holder.sILogger.w(message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        Holder.sILogger.i(message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        Holder.sILogger.v(message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        Holder.sILogger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     *
     * @param json
     */
    public static void json(@Nullable String json) {
        Holder.sILogger.json(json);
    }

    /**
     * Formats the given xml content and print it
     *
     * @param xml
     */
    public static void xml(@Nullable String xml) {
        Holder.sILogger.xml(xml);
    }

    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        Holder.sILogger.log(priority, tag, message, throwable);
    }

    public static void clearLogAdapters() {
        Holder.sILogger.clearLogAdapters();
    }
}
