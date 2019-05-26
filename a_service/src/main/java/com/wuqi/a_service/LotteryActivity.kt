package com.wuqi.a_service

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SizeUtils
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.sdk.multitype.BaseAdapter
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.router.Path
import com.weyee.sdk.toast.ToastUtils
import com.wuqi.a_service.di.DaggerLotteryComponent
import com.wuqi.a_service.di.LotteryModule
import com.wuqi.a_service.wan.LotteryContract
import com.wuqi.a_service.wan.LotteryPresenter
import com.wuqi.a_service.wan.LotteryWapperCategoryAndInfo
import kotlinx.android.synthetic.main.activity_lottery.*


@Route(path = Path.Service + "Lottery")
class LotteryActivity : BaseActivity<LotteryPresenter>(), LotteryContract.View {

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerLotteryComponent
            .builder()
            .appComponent(appComponent)
            .lotteryModule(LotteryModule(this))
            .build()
            .inject(this@LotteryActivity)
    }

    override fun getResourceId(): Int = R.layout.activity_lottery

    override fun initView(savedInstanceState: Bundle?) {
        headerView.setTitle("Lottery")
        refreshView.setEnableLoadMore(false)
        refreshView.autoRefresh()
        refreshView.setOnRefreshListener {
            mPresenter.home()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(context).margin(SizeUtils.dp2px(16f)).size(
                1
            ).build()
        )
        recyclerView.adapter =
            object : BaseAdapter<LotteryWapperCategoryAndInfo>(null, { view, viewType, data, position ->
                ToastUtils.show(data.category.lottery_name)
            }) {
                override fun getHolder(v: View, viewType: Int): BaseHolder<LotteryWapperCategoryAndInfo> {
                    return object : BaseHolder<LotteryWapperCategoryAndInfo>(v) {
                        override fun setData(data: LotteryWapperCategoryAndInfo, position: Int) {
                            setText(R.id.tvName, data.category.lottery_name)
                            setText(R.id.tvPeriods, String.format("第%s期", data.info.lottery_no))
                            setText(
                                R.id.tvRemark, when {
                                    data.category.remarks.contains(Regex("每周\\S*开奖")) -> data.category.remarks.replace(
                                        "每周",
                                        ""
                                    ).replace("开奖", "")
                                    data.category.remarks.contains(Regex("每日开奖")) -> "一、...、日"
                                    else -> ""
                                }
                            )
                            setText(
                                R.id.tvPrize,
                                String.format(
                                    "奖池：%s元",
                                    if (TextUtils.isEmpty(data.info.lottery_pool_amount)) "0" else data.info.lottery_pool_amount
                                )
                            )

                            val gridView = getView<GridView>(R.id.gridView)
                            val balls = data.info.lottery_res.split(",")
                            gridView.adapter =
                                object : ArrayAdapter<String>(itemView.context, R.layout.item_lottery_ball, balls) {
                                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                                        val view = super.getView(position, convertView, parent)
                                        val color: String = when (data.category.lottery_id) {
                                            "ssq" -> if (position == balls.size - 1) "#158ad2" else "#e34c4c"
                                            "dlt" -> if (position == balls.size - 1 || position == balls.size - 2) "#158ad2" else "#e34c4c"
                                            "qlc" -> if (position == balls.size - 1) "#158ad2" else "#e34c4c"
                                            else -> "#e34c4c"
                                        }
                                        (view as TextView).setTextColor(Color.parseColor(color))
                                        return view
                                    }
                                }
                        }

                    }
                }

                override fun getLayoutId(viewType: Int): Int = R.layout.item_lottery

            }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun useProgressAble(): Boolean {
        return !super.useProgressAble()
    }

    override fun setHomeData(list: List<LotteryWapperCategoryAndInfo>?) {
        (recyclerView.adapter as BaseAdapter<LotteryWapperCategoryAndInfo>).addAll(list, true)
    }

    override fun onCompleted() {
        refreshView.finishRefresh()
    }
}
