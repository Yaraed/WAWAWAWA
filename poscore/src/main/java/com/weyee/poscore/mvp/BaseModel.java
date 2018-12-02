package com.weyee.poscore.mvp;

import com.weyee.poscore.base.integration.IRepositoryManager;

/**
 * Created by liu-feng on 2017/6/5.
 */
public class BaseModel implements IModel {
    protected IRepositoryManager mRepositoryManager;//用于管理网络请求层,以及数据缓存层

    public BaseModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }
}
