package com.wuqi.a_gpuimage.di

import com.weyee.poscore.base.integration.IRepositoryManager
import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BaseModel
import com.wuqi.a_gpuimage.model.ImageService
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/12.
 */
@ActivityScope
class ImageModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    ImageContract.Model {
    override fun getImages(pageSize: Int, pageIndex: Int): Observable<List<String>> {
        return mRepositoryManager.obtainRetrofitService(ImageService::class.java).getImages(pageSize, pageIndex).map {
            var list = mutableListOf<String>()
            val iterator = it.results.listIterator()
            iterator.forEach { it1 ->
                list.add(it1.url)
            }
            return@map list
        }
    }
}