package com.weyee.sdk.api.core;

/**
 * <p>进度条接口。</p>
 *
 * @author moguangjian
 * @date 2018/6/25
 */
public interface ProgressAble {

    /**
     * 显示进度界面。
     */
    void showProgress();

    /**
     * 隐藏进度界面。
     */
    void hideProgress();

    /**
     * 添加请求记录数。该方法记录同一页面所有请求数，用来判断是否请求完所有请求
     */
    void addRequestRecordCount();

    /**
     * 减少请求记录数。该方法记录同一页面所有请求数，用来判断是否请求完所有请求
     */
    void reduceRequestRecordCount();

    /**
     * 是否请求完所有接口
     */
    boolean isRequestAllFinish();
}
