package com.wuqi.a_service.di

import com.weyee.poscore.di.scope.ActivityScope
import com.wuqi.a_service.wan.LotteryContract
import dagger.Module
import dagger.Provides

/**
 *
 * @author wuqi by 2019/4/12.
 */
@Module
class LotteryModule(val view: LotteryContract.View) {
    @ActivityScope
    @Provides
    fun provideLotteryView(): LotteryContract.View = view

    @ActivityScope
    @Provides
    fun provideLotteryModel(model: LotteryContract.Model): LotteryContract.Model = model
}