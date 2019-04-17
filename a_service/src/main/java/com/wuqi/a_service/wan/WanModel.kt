package com.wuqi.a_service.wan

import com.weyee.poscore.base.integration.IRepositoryManager
import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BaseModel
import com.wuqi.a_service.services.WanService
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/17.
 */
@ActivityScope
class WanModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager),
    WanContract.Model {
    override fun articles(page: Int): Observable<ArticleBean> {
        return mRepositoryManager.obtainRetrofitService(WanService::class.java).articles(page).map { it.data }
    }

    override fun banners(): Observable<List<BannerBean>> {
        return mRepositoryManager.obtainRetrofitService(WanService::class.java).banners().map { it.data }
    }
}