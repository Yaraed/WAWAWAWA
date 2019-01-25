package com.weyee.sdk.api.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.weyee.sdk.api.core.exception.ApiExceptionAble;
import com.weyee.sdk.api.core.exception.ExceptionHandle;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <p>一句话描述。</p>
 *
 * @author moguangjian
 * @date 2018/6/22
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public static final String TAG = BaseObserver.class.getSimpleName();

    private SoftReference<ProgressAble> softReference;
    private boolean isShowProgress = true;

    public BaseObserver() {
        init(null, true);
    }

    public BaseObserver(Context context) {
        init(context, true);
    }

    public BaseObserver(Context context, boolean isShowProgress) {
        init(context, isShowProgress);

    }

    private void init(Context context, boolean isShowProgress) {
        this.isShowProgress = isShowProgress;

        if (context == null
                || !(context instanceof ProgressAble)) {
            return;
        }

        ProgressAble progressAble = ((ProgressAble) context);

        if (progressAble != null) {
            softReference = new SoftReference<>(progressAble);
        }
    }

    /**
     * 成功回调。
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    @Override
    public void onSubscribe(Disposable d) {
        if (!isShowProgress || softReference == null) {
            return;
        }

        ProgressAble progressAble = softReference.get();
        if (progressAble == null) {
            return;
        }

        if (progressAble == null) {
            return;
        }

        progressAble.showProgress();
        progressAble.addRequestRecordCount();
    }

    @Override
    public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e, false);
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(e, true);
    }


    public void onError(Throwable e, boolean isHideProgress) {
        Exception exception = ExceptionHandle.handleException(e);

        if (isHideProgress) {
            hideProgress();
        }

        String msg = "未知异常！";

        if (exception instanceof ApiExceptionAble) {
            msg = exception.getMessage();
        }
        Log.e(TAG, msg);

        boolean isHideDefaultHint = ApiConfig.errorHintAble != null && !ApiConfig.errorHintAble.isShowDefaultHint();
        if (isHideDefaultHint) {
            return;
        }

        Toast.makeText(ApiFactory.context,  msg, Toast.LENGTH_SHORT).show();
    }

    private void hideProgress() {
        if (!isShowProgress || softReference == null) {
            return;
        }

        ProgressAble progressAble = softReference.get();
        if (progressAble == null) {
            return;
        }

        progressAble.reduceRequestRecordCount();

        if (!progressAble.isRequestAllFinish()) {
            return;
        }

        progressAble.hideProgress();
    }

    @Override
    public void onComplete() {
        hideProgress();
    }
}
