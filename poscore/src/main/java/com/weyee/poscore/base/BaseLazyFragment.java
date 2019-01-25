package com.weyee.poscore.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.weyee.poscore.base.delegate.IFragment;
import com.weyee.poscore.mvp.IPresenter;
import com.weyee.posres.arch.MFragment;

import javax.inject.Inject;

/**
 * Created by liu-feng on 2017/6/5.
 */
public abstract class BaseLazyFragment<P extends IPresenter> extends MFragment implements IFragment {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;

    protected View mRootView;
    /**
     * 当前是否进行过懒加载
     */
    private boolean isLazyLoad;
    /**
     * 当前 Fragment 是否可见
     */
    private boolean isFragmentVisible;


    public BaseLazyFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mRootView == null && getResourceId() > 0) {
            mRootView = inflater.inflate(getResourceId(), null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mPresenter != null) mPresenter.onDestroy();
        this.mPresenter = null;
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

    /**
     * 是否已经加载布局并初始化
     * @return
     */
    protected boolean isLazyLoad() {
        return isLazyLoad;
    }
}
