package com.weyee.poscore.mvp;

import android.os.Handler;
import android.os.Message;


import com.weyee.sdk.event.EventBus;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by liu-feng on 2017/6/5.
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected CompositeDisposable mCompositeDisposable;
    protected MHandler mHandler;

    protected M mModel;
    protected V mView;

    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mView = rootView;
        onAttach();
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
        onAttach();
    }

    public BasePresenter() {
        onAttach();
    }


    @Override
    public void onAttach() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.register(this);//注册eventbus
    }

    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.unregister(this);//解除注册eventbus
        unDispose();//解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }


    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有disposable放入,集中处理
    }

    protected void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }

    protected void handleMessage(Message msg) {
    }


    public static class MHandler extends Handler {
        private final WeakReference<BasePresenter> activityWeakReference;

        public MHandler(BasePresenter presenter) {
            activityWeakReference = new WeakReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            BasePresenter presenter = activityWeakReference.get();
            if (presenter != null) {
                presenter.handleMessage(msg);
            }
        }
    }


}
