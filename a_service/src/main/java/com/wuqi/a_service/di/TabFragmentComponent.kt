package com.wuqi.a_service.di

import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.di.scope.FragmentScope
import com.wuqi.a_service.TabLayoutFragment
import dagger.Component

/**
 *
 * @author wuqi by 2019/4/12.
 */
@FragmentScope
@Component(modules = [TabModule::class], dependencies = [AppComponent::class])
interface TabFragmentComponent {
    fun inject(activity: TabLayoutFragment)
}