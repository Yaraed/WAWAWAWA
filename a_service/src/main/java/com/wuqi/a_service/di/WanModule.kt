package com.wuqi.a_service.di

import com.weyee.poscore.di.scope.ActivityScope
import com.wuqi.a_service.wan.WanContract
import dagger.Module
import dagger.Provides

/**
 *
 * @author wuqi by 2019/4/12.
 */
@Module
class WanModule(val view: WanContract.WanBaseView) {
    @ActivityScope
    @Provides
    fun provideWanView(): WanContract.WanBaseView = view

    @ActivityScope
    @Provides
    fun provideWanModel(model: WanContract.Model): WanContract.Model = model
}