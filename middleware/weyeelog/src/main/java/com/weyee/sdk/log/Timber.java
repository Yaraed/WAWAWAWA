package com.weyee.sdk.log;

import androidx.annotation.NonNull;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Printer;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
public class Timber {

    public static void addAdapter(@NonNull LogAdapter adapter) {

    }


    public static Printer t(String tag) {
        return null;
    }


    public static void d(@NonNull String message, Object... args) {
        timber.log.Timber.d(message, args);
    }

    public static void e(@NonNull String message, Object... args) {
        timber.log.Timber.e(message, args);
    }


    public static void e(Throwable throwable, @NonNull String message, Object... args) {
        timber.log.Timber.e(throwable, message, args);
    }


    public static void w(@NonNull String message, Object... args) {
        timber.log.Timber.w(message, args);
    }


    public static void i(@NonNull String message, Object... args) {
        timber.log.Timber.i(message, args);
    }


    public static void v(@NonNull String message, Object... args) {
        timber.log.Timber.v(message, args);
    }


    public static void wtf(@NonNull String message, Object... args) {
        timber.log.Timber.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     *
     * @param json
     */

    public static void json(String json) {

    }

    /**
     * Formats the given xml content and print it
     *
     * @param xml
     */

    public static void xml(String xml) {

    }


    public static void log(int priority, String tag, String message, Throwable throwable) {
        timber.log.Timber.log(priority, throwable, message);
    }


    public static void clearLogAdapters() {

    }
}
