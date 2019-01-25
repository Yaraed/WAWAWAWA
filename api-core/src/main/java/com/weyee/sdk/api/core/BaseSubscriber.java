package com.weyee.sdk.api.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.weyee.sdk.api.core.exception.ApiException;
import com.weyee.sdk.api.core.exception.ApiExceptionAble;
import com.weyee.sdk.api.core.exception.ErrorCode;
import com.weyee.sdk.api.core.exception.ExceptionHandle;

import java.lang.ref.SoftReference;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * <p>网络请求订阅者，内部异常处理，支持背压</p>
 *
 * @author: mcgrady
 * @date: 2018/7/11
 */

public class BaseSubscriber<T> extends ResourceSubscriber<T> {

    public static final String TAG = BaseSubscriber.class.getSimpleName();

    private SubscriberCallback<T> callback;

    private SoftReference<ProgressAble> softReference;
    private boolean isShowProgress = true;

    private BaseSubscriber() {
    }

    public BaseSubscriber(Context context, SubscriberCallback<T> callback) {
        this(context, true, callback);
    }

    public BaseSubscriber(Context context, boolean isShowProgress, SubscriberCallback<T> callback) {
        this.isShowProgress = isShowProgress;
        this.callback = callback;

        if (context == null || !(context instanceof ProgressAble)) {
            return;
        }

        ProgressAble progressAble = ((ProgressAble) context);

        if (progressAble != null) {
            softReference = new SoftReference<>(progressAble);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        showProgress();
    }

    @Override
    public void onNext(T response) {
        Log.d(TAG, "onNext");
        if (callback != null) {
            try {
                callback.onSuccess(response);
            } catch (Exception e) {
                e.printStackTrace();
                onError(e, false);
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        onError(t, true);
        onComplete();
    }

    private void onError(Throwable e, boolean isHideProgress) {
        Exception exception = ExceptionHandle.handleException(e);

        if (isHideProgress) {
            hideProgress();
        }

        String msg = "未知异常！";
        if (exception instanceof ApiExceptionAble) {
            msg = exception.getMessage();
        }
        Log.e(TAG, "onError: " + msg);
        if (ApiConfig.errorHintAble == null || ApiConfig.errorHintAble.isShowDefaultHint()) {
            Toast.makeText(ApiFactory.context,  msg, Toast.LENGTH_SHORT).show();
        }

        if (callback != null) {
            callback.onFailure(getExceptionCode(exception), msg);
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        hideProgress();

        if (callback != null) {
            callback.onCompleted();
        }

        if (!this.isDisposed()) {
            this.dispose();
        }
    }

    private void showProgress() {
        if (isShowProgress && softReference != null) {

            ProgressAble progressAble = softReference.get();
            if (progressAble != null) {

                progressAble.showProgress();
                progressAble.addRequestRecordCount();
            }
        }
    }

    private void hideProgress() {
        if (isShowProgress && softReference != null) {

            ProgressAble progressAble = softReference.get();
            if (progressAble != null) {

                progressAble.reduceRequestRecordCount();
                if (progressAble.isRequestAllFinish()) {

                    progressAble.hideProgress();
                }
            }
        }
    }

    private String getExceptionMsg(Exception e) {
        return e.getMessage();
    }

    private int getExceptionCode(Exception e) {
        if (e instanceof ApiException) {
            return ((ApiException) e).getCode();
        }

        return ErrorCode.UNKNOWN;
    }
}
