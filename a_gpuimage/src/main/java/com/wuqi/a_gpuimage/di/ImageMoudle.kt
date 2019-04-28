package com.wuqi.a_gpuimage.di

import com.weyee.poscore.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 *
 * @author wuqi by 2019/4/12.
 */
@Module
class ImageMoudle(val view: ImageContract.ImageView) {
    @ActivityScope
    @Provides
    fun provideImageView(): ImageContract.ImageView = view

    @ActivityScope
    @Provides
    fun provideImageModel(model: ImageContract.Model): ImageContract.Model = model
}