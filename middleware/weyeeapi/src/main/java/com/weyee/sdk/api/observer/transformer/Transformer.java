package com.weyee.sdk.api.observer.transformer;

import android.app.Dialog;
import com.weyee.sdk.api.observer.listener.ProgressAble;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>控制操作线程的辅助类
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/11 0011
 */
public class Transformer {
    /**
     * 无参数
     *
     * @param <T> 泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return switchSchedulers((Dialog) null);
    }

    /**
     * 带参数  显示loading对话框
     *
     * @param dialog loading
     * @param <T>    泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(final Dialog dialog) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (dialog != null) {
                        dialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally((Action) () -> {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 带参数  显示loading对话框
     *
     * @param progressAble loading
     * @param <T>    泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(final ProgressAble progressAble) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (progressAble != null) {
                        progressAble.showProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally((Action) () -> {
                    if (progressAble != null) {
                        progressAble.hideProgress();
                    }
                });
    }
}
