package com.wuqi.a_service.wan

import com.weyee.poscore.base.integration.IRepositoryManager
import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BaseModel
import com.wuqi.a_service.services.LotteryCache
import com.wuqi.a_service.services.LotteryService
import com.wuqi.a_service.wan.cache.CategoryCache
import com.wuqi.a_service.wan.cache.InfoCache
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/17.
 */
@ActivityScope
class LotteryModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager),
    LotteryContract.Model {
    override fun lotterys(): Observable<List<LotteryCategory>> {
        return mRepositoryManager.obtainCacheService(LotteryCache::class.java).lotterys(
            mRepositoryManager.obtainRetrofitService(LotteryService::class.java).lotterys().map {
                CategoryCache(it.error_code,it.reason,it.result)
            },
            DynamicKey(LotteryService.ApiKey),
            EvictDynamicKey(false)
        ).map { it.result }
    }

    override fun infos(lottery_id: String, lottery_no: String?): Observable<LotteryInfo> {
        return mRepositoryManager.obtainCacheService(LotteryCache::class.java).infos(
            mRepositoryManager.obtainRetrofitService(LotteryService::class.java).infos(lottery_id, lottery_no).map {
                InfoCache(it.error_code,it.reason,it.result)
            },
            DynamicKey(String.format("%s%s%s", LotteryService.ApiKey, lottery_id, lottery_no)),
            EvictDynamicKey(false)
        ).map { it.result }
    }

    override fun historys(lottery_id: String, page: Int, page_size: Int): Observable<LotteryHistory> {
        return mRepositoryManager.obtainRetrofitService(LotteryService::class.java)
            .historys(lottery_id, page, page_size).map { it.result }
    }

    override fun bonus(lottery_id: String, lottery_no: String, lottery_res: String): Observable<LotteryBonus> {
        return mRepositoryManager.obtainRetrofitService(LotteryService::class.java)
            .bonus(lottery_id, lottery_no, lottery_res).map { it.result }
    }


}