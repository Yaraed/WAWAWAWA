package com.wuqi.a_gpuimage.di

import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.di.scope.ActivityScope
import com.wuqi.a_gpuimage.ImageActivity
import dagger.Component

/**
 *
 * @author wuqi by 2019/4/12.
 */
@ActivityScope
@Component(modules = [ImageMoudle::class], dependencies = [AppComponent::class])
interface ImageComponent {
    fun inject(activity: ImageActivity)
}