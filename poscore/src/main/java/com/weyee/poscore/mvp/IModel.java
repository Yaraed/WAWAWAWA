package com.weyee.poscore.mvp;

import androidx.lifecycle.LifecycleObserver;

/**
 * Created by liu-feng on 2017/6/5.
 */
public interface IModel extends LifecycleObserver {
    void onDestroy();
}
