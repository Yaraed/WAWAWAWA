package com.weyee.poscore.mvp;

import android.app.Service;
import android.os.Handler;
import android.os.Message;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.weyee.sdk.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

import java.lang.ref.WeakReference;

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
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
            if (mModel != null) {
                ((LifecycleOwner) mView).getLifecycle().addObserver(mModel);
            }
        }
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
     * 只有当 {@code mView} 不为 null, 并且 {@code mView} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     * 所以当您想在 {@link Service} 以及一些自定义 {@link android.view.View} 或自定义类中使用 {@code Presenter} 时
     * 您也将不能继续使用 {@link OnLifecycleEvent} 绑定生命周期
     *
     * @param owner link {@link androidx.core.app.ComponentActivity} and {@link androidx.fragment.app.Fragment}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        /**
         * 注意, 如果在这里调用了 {@link #onDestroy()} 方法, 会出现某些地方引用 {@code mModel} 或 {@code mView} 为 null 的情况
         * 比如在 {@link RxLifecycle} 终止 {@link Observable} 时, 在 {@link io.reactivex.Observable#doFinally(Action)} 中却引用了 {@code mView} 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 {@code mModel} 或 {@code mView} 也可能会出现此情况
         */
        owner.getLifecycle().removeObserver(this);
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
