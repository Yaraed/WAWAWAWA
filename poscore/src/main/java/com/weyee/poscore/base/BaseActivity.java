package com.weyee.poscore.base;

import androidx.fragment.app.FragmentManager;
import com.weyee.poscore.base.delegate.IActivity;
import com.weyee.poscore.mvp.IPresenter;
import com.weyee.posres.arch.MActivity;

import javax.inject.Inject;

/**
 * Created by liu-feng on 2017/6/5.
 */
public abstract class BaseActivity<P extends IPresenter> extends MActivity implements IActivity {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;

    @Override
    protected void onDestroy() {
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
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }
}


