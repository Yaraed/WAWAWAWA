package com.weyee.poscore.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.weyee.poscore.base.delegate.IFragment;
import com.weyee.poscore.mvp.IPresenter;
import com.weyee.possupport.arch.MFragment;

import javax.inject.Inject;

/**
 * Created by liu-feng on 2017/6/5.
 */
public abstract class BaseFragment<P extends IPresenter> extends MFragment implements IFragment {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;


    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments(),但是这里有一个问题是，如果Fragment不是主动销毁的，系统重建时Fragment中的Bundle就无法保存了
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
