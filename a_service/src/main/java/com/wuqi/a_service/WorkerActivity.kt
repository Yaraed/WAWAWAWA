package com.wuqi.a_service

import android.os.Bundle
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.Path
import java.util.*

/**
 * 演示WorkerManger
 */
@Route(path = Path.Service + "Worker")
class WorkerActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private var workerId: UUID? = null
    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun getResourceId(): Int = R.layout.activity_worker

    override fun initView(savedInstanceState: Bundle?) {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresCharging(true)
//            .build()

        val workerT = OneTimeWorkRequest.Builder(WorkerT::class.java)
//            .setConstraints(constraints)
            .build()

        val workerT2 = OneTimeWorkRequest.Builder(WorkerT2::class.java)
//            .setConstraints(constraints)
            .build()

        val workerT3 = OneTimeWorkRequest.Builder(WorkerT3::class.java)
//            .setConstraints(constraints)
            .build()


        WorkManager.getInstance()
            .beginWith(workerT2)
            .then(workerT)
            .then(workerT3)
            .enqueue()
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance().cancelAllWork()
    }

}
