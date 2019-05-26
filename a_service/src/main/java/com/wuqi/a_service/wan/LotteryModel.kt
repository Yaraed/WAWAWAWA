package com.wuqi.a_service.wan

import com.weyee.poscore.base.integration.IRepositoryManager
import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BaseModel
import com.wuqi.a_service.services.LotteryService
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/17.
 */
@ActivityScope
class LotteryModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager),
    LotteryContract.Model {
    override fun lotterys(): Observable<List<LotteryCategory>> {
        return mRepositoryManager.obtainRetrofitService(LotteryService::class.java).lotterys().map { it.result }
    }

    override fun infos(lottery_id: String, lottery_no: String?): Observable<LotteryInfo> {
        return mRepositoryManager.obtainRetrofitService(LotteryService::class.java).infos(lottery_id, lottery_no)
            .map { it.result }
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