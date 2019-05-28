package com.wuqi.a_service.di

import com.weyee.poscore.di.scope.FragmentScope
import com.wuqi.a_service.wan.WanContract
import dagger.Module
import dagger.Provides

/**
 *
 * @author wuqi by 2019/4/12.
 */
@Module
class TabModule(val view: WanContract.WanBaseView) {
    @FragmentScope
    @Provides
    fun provideWanView(): WanContract.WanBaseView = view

    @FragmentScope
    @Provides
    fun provideWanModel(model: WanContract.Model): WanContract.Model = model
}