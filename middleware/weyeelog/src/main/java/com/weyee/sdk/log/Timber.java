package com.weyee.sdk.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Printer;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
class Timber implements ILogger {
    @Override
    public void addAdapter(@Nullable LogAdapter adapter) {

    }

    @Override
    public Printer t(String tag) {
        return null;
    }

    @Override
    public void d(@NonNull String message, Object... args) {
        timber.log.Timber.d(message, args);
    }

    @Override
    public void d(@Nullable Object object) {
        timber.log.Timber.d(null, object);
    }

    @Override
    public void e(@NonNull String message, Object... args) {
        timber.log.Timber.e(message, args);
    }

    @Override
    public void e(Throwable throwable, @NonNull String message, Object... args) {
        timber.log.Timber.e(throwable, message, args);
    }

    @Override
    public void w(@NonNull String message, Object... args) {
        timber.log.Timber.w(message, args);
    }

    @Override
    public void i(@NonNull String message, Object... args) {
        timber.log.Timber.i(message, args);
    }

    @Override
    public void v(@NonNull String message, Object... args) {
        timber.log.Timber.v(message, args);
    }

    @Override
    public void wtf(@NonNull String message, Object... args) {
        timber.log.Timber.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     *
     * @param json
     */
    @Override
    public void json(String json) {

    }

    /**
     * Formats the given xml content and print it
     *
     * @param xml
     */
    @Override
    public void xml(String xml) {

    }

    @Override
    public void log(int priority, String tag, String message, Throwable throwable) {
        timber.log.Timber.log(priority, throwable, message);
    }

    @Override
    public void clearLogAdapters() {

    }
}
