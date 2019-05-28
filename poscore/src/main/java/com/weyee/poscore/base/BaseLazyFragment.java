package com.weyee.poscore.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.weyee.poscore.mvp.IPresenter;

import java.util.List;

/**
 * 快速实现Fragment懒加载
 * Created by liu-feng on 2017/6/5.
 */
public abstract class BaseLazyFragment<P extends IPresenter> extends BaseFragment<P> {

    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求

    public BaseLazyFragment() {
    }

    @Override
    public void initView(Bundle savedanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * 此处实现具体的数据请求逻辑
     */
    protected abstract void lazyLoadData();

    /**
     * 注意：该方法只会在FragmentPagerAdapter才会去调用，普通Activity中装载Fragment是不会调用的
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoadData();
    }

    /**
     * 注意：该方法只会在show/hide方法调用时才会去调用
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isVisibleToUser = !hidden;
        tryLoadData();
    }

    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        tryLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isVisibleToUser = false;
        isDataLoaded = false;
    }

    /**
     * 当Fragment是系统销毁的时候，保存Bundle信息，系统重建时会保存这个信息，只要重新走onCreate流程获取Bundle信息就可以了
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof BaseLazyFragment && ((BaseLazyFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseLazyFragment && ((BaseLazyFragment) child).isVisibleToUser) {
                ((BaseLazyFragment) child).tryLoadData();
            }
        }
    }

    private void tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && !isDataLoaded) {
            lazyLoadData();
            isDataLoaded = true;
            //通知子Fragment请求数据
            dispatchParentVisibleState();
        }
    }
}
