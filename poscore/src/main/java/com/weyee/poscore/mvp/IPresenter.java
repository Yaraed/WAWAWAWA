package com.weyee.poscore.mvp;

import androidx.lifecycle.LifecycleObserver;

/**
 * Created by liu-feng on 2017/6/5.
 */
public interface IPresenter extends LifecycleObserver {
    void onAttach();
    void onDestroy();
}
