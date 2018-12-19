package com.letion.app

import com.letion.app.pojo.BookBean
import com.letion.app.pojo.Top250Bean
import com.weyee.poscore.base.integration.IRepositoryManager
import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BaseModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/19 0019
 */
@ActivityScope
class MainModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    MainContract.Model {
    override fun getBook(maps: Map<String, Any>): Observable<BookBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getBook(maps)
    }

    override fun getTop250(count: Int): Observable<Top250Bean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getTop250(count)
    }

    override fun getBookString(): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).bookString
    }
}