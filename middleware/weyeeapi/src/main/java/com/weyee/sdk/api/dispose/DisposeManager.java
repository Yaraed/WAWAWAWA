package com.weyee.sdk.api.dispose;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 管理请求--用于取消请求时候使用
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class DisposeManager implements IDispose<Object> {

    private static DisposeManager mInstance = null;

    private HashMap<Object, CompositeDisposable> mMaps;

    public static DisposeManager get() {
        if (mInstance == null) {
            synchronized (DisposeManager.class) {
                if (mInstance == null) {
                    mInstance = new DisposeManager();
                }
            }
        }
        return mInstance;
    }

    private DisposeManager() {
        mMaps = new HashMap<>();
    }

    @Override
    public void add(Object tag, Disposable disposable) {
        if (null == tag) {
            return;
        }
        //tag下的一组或一个请求，用来处理一个页面的所以请求或者某个请求
        //设置一个相同的tag就行就可以取消当前页面所有请求或者某个请求了
        CompositeDisposable compositeDisposable = mMaps.get(tag);
        if (compositeDisposable == null) {
            CompositeDisposable compositeDisposableNew = new CompositeDisposable();
            compositeDisposableNew.add(disposable);
            mMaps.put(tag, compositeDisposableNew);
        } else {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void remove(Object tag) {
        if (null == tag) {
            return;
        }
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag);
        }
    }

    @Override
    public void cancel(Object tag) {
        if (null == tag) {
            return;
        }
        if (mMaps.isEmpty()) {
            return;
        }
        if (null == mMaps.get(tag)) {
            return;
        }
        if (!mMaps.get(tag).isDisposed()) {
            mMaps.get(tag).dispose();
            mMaps.remove(tag);
        }
    }

    @Override
    public void cancel(Object... tags) {
        if (null == tags) {
            return;
        }
        for (Object tag : tags) {
            cancel(tag);
        }
    }

    @Override
    public void cancelAll() {
        if (mMaps.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<Object, CompositeDisposable>> it = mMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = it.next();
            Object tag = entry.getKey();
            cancel(tag);
        }
    }
}
