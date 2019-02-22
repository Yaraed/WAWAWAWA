package com.weyee.poscore.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weyee.poscore.base.delegate.IFragment;
import com.weyee.poscore.mvp.IPresenter;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.weyee.possupport.arch.MFragment;

/**
 * Created by liu-feng on 2017/6/5.
 */
public abstract class BaseFragment<P extends IPresenter> extends MFragment implements IFragment {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;


    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return inflater.inflate(getResourceId(), container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mPresenter != null) mPresenter.onDestroy(); this.mPresenter = null;
    }


    /**
     * 是否使用eventBus,默认为使用(false)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return false;
    }
}
