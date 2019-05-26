package com.wuqi.a_service.wan

import com.weyee.poscore.di.scope.ActivityScope
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 *
 * @author wuqi by 2019/4/17.
 */
@ActivityScope
class LotteryPresenter @Inject constructor(model: LotteryModel?, rootView: LotteryContract.View?) :
    BasePresenter<LotteryContract.Model, LotteryContract.View>(model, rootView) {

    fun home() {
        mModel.lotterys()
            .flatMap(Function<List<LotteryCategory>, ObservableSource<List<LotteryWapperCategoryAndInfo>>> {
                return@Function Observable.zip(it.map { category -> mModel.infos(category.lottery_id, null) }
                ) { infos ->
                    infos.map { info ->
                        info as LotteryInfo
                        LotteryWapperCategoryAndInfo(
                            it.find { predicate -> info.lottery_id == predicate.lottery_id && info.lottery_name == predicate.lottery_name }!!,
                            info
                        )
                    }
                }
            })
            .compose(Transformer.switchSchedulers())
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<List<LotteryWapperCategoryAndInfo>>() {
                override fun onSuccess(t: List<LotteryWapperCategoryAndInfo>?) {
                    mView.setHomeData(t)
                }

                override fun onCompleted() {
                    super.onCompleted()
                    mView.onCompleted()
                }

            })

    }

    fun lotterys() {
        mModel.lotterys()
            .compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<List<LotteryCategory>>() {
                override fun onSuccess(t: List<LotteryCategory>?) {
                }

            })
    }

    fun infos(lottery_id: String, lottery_no: String) {
        mModel.infos(lottery_id, lottery_no)
            .compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<LotteryInfo>() {
                override fun onSuccess(t: LotteryInfo?) {
                }

            })
    }

    fun historys(lottery_id: String, page: Int = 1, page_size: Int = 10) {
        mModel.historys(lottery_id, page, page_size)
            .compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<LotteryHistory>() {
                override fun onSuccess(t: LotteryHistory?) {
                }

            })
    }

    fun bonus(lottery_id: String, lottery_no: String, lottery_res: String) {
        mModel.bonus(lottery_id, lottery_no, lottery_res)
            .compose(Transformer.switchSchedulers(progressAble))
            .`as`(RxLiftUtils.bindLifecycle(lifecycleOwner))
            .subscribe(object : ProgressSubscriber<LotteryBonus>() {
                override fun onSuccess(t: LotteryBonus?) {
                }

            })
    }
}