package com.letion.app.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.RemoteViews
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.letion.app.R
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.multitype.BaseAdapter
import com.weyee.sdk.multitype.BaseHolder
import com.weyee.sdk.multitype.OnRecyclerViewItemClickListener
import com.weyee.sdk.router.Path
import com.weyee.sdk.util.notify.NotificationUtils
import com.wuqi.a_http.TestActivity
import kotlinx.android.synthetic.main.activity_notify.*


@Route(path = Path.MAIN + "NotifyFit8")
class NotifyFit8Activity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private var mNotificationManager: NotificationManager? = null

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_notify

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this@NotifyFit8Activity)

        recyclerView.adapter = object : BaseAdapter<String>(null,
            OnRecyclerViewItemClickListener<String> { _, _, _, position ->
                when (position) {
                    0 -> cancelAllNotification()
                    1 -> sendNotification1()
                    2 -> sendNotification2()
                    3 -> sendNotification3()
                    4 -> sendNotification4()
                    5 -> {
                    }
                    6 -> ssendNotification151()
                    7 -> sendNotification8()
                    8 -> sendNotification9()
                    9 -> sendNotification10()
                    10 -> sendNotification11()
                    11 -> sendNotification12()
                    12 -> sendNotification13()
                    13 -> sendNotification14()
                    14 -> sendNotification15()
                }
            }) {
            override fun getHolder(v: View, viewType: Int): BaseHolder<String> {
                return object : BaseHolder<String>(v) {
                    override fun setData(data: String, position: Int) {
                        setText(android.R.id.text1, data)
                    }

                }
            }

            override fun getLayoutId(viewType: Int): Int = android.R.layout.simple_list_item_1


        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val data = resources.getStringArray(R.array.notify_entry_fit8).asList()
        (recyclerView.adapter as BaseAdapter<String>).addAll(data)
        initNotificationManager()
    }

    private fun initNotificationManager() {
        // 创建一个NotificationManager的引用
        mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


    private fun cancelAllNotification() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils.clearNotification()
    }


    private fun sendNotification1() {
        //这三个属性是必须要的，否则异常
        val notificationUtils = NotificationUtils(this)
        notificationUtils.sendNotification(1, "这个是标题", "这个是内容", R.mipmap.ic_launcher)

    }


    private fun sendNotification2() {
        //处理点击Notification的逻辑
        //创建intent
        val resultIntent = Intent(this, TestActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)           //添加为栈顶Activity
        resultIntent.putExtra("what", 2)
        val resultPendingIntent = PendingIntent.getActivity(this, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 定义Notification的各种属性
        val notificationUtils = NotificationUtils(this)
        notificationUtils
            .setContentIntent(resultPendingIntent)
            .sendNotificationCompat(2, "这个是标题2", "这个是内容2", R.mipmap.ic_launcher)
    }


    private fun sendNotification3() {
        val vibrate = longArrayOf(0, 500, 1000, 1500)
        //处理点击Notification的逻辑
        //创建intent
        val resultIntent = Intent(this, TestActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)           //添加为栈顶Activity
        resultIntent.putExtra("what", 3)
        val resultPendingIntent = PendingIntent.getActivity(this, 3, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //发送pendingIntent

        val notificationUtils = NotificationUtils(this)
        notificationUtils
            //让通知左右滑的时候是否可以取消通知
            .setOngoing(true)
            //设置内容点击处理intent
            .setContentIntent(resultPendingIntent)
            //设置状态栏的标题
            .setTicker("来通知消息啦")
            //设置自定义view通知栏布局
            .setContent(getRemoteViews())
            //设置sound
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
            //设置优先级
            .setPriority(Notification.PRIORITY_DEFAULT)
            //自定义震动效果
            .setVibrate(vibrate)
            //必须设置的属性，发送通知
            .sendNotification(3, "这个是标题3", "这个是内容3", R.mipmap.ic_launcher)
    }


    private fun sendNotification4() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils.setContent(getRemoteViews())
        val notification = notificationUtils.getNotification("这个是标题4", "这个是内容4", R.mipmap.ic_launcher)
        notificationUtils.getManager().notify(4, notification)
    }


    private fun getRemoteViews(): RemoteViews {
        val remoteViews = RemoteViews(packageName, R.layout.notification_mobile_play)
        // 设置 点击通知栏的上一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getActivityPendingIntent(11))
        // 设置 点击通知栏的下一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getActivityPendingIntent(12))
        // 设置 点击通知栏的播放暂停按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_start, getActivityPendingIntent(13))
        // 设置 点击通知栏的根容器时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(14))
        remoteViews.setTextViewText(R.id.tv_title, "标题")     // 设置通知栏上标题
        remoteViews.setTextViewText(R.id.tv_artist, "艺术家")   // 设置通知栏上艺术家
        return remoteViews
    }


    /** 获取一个Activity类型的PendingIntent对象  */
    private fun getActivityPendingIntent(what: Int): PendingIntent {
        val intent = Intent(this, TestActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)           //添加为栈顶Activity
        intent.putExtra("what", what)
        return PendingIntent.getActivity(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    private fun sendNotification8() {
        for (a in 0..2) {
            //这三个属性是必须要的，否则异常
            val notificationUtils = NotificationUtils(this)
            notificationUtils.sendNotification(8, "这个是标题8", "这个是内容8", R.mipmap.ic_launcher)

        }
    }


    private fun sendNotification9() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils
            //让通知左右滑的时候是否可以取消通知
            .setOngoing(true)
            //设置状态栏的标题
            .setTicker("有新消息呢9")
            //设置自定义view通知栏布局
            .setContent(getRemoteViews())
            //设置sound
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
            //设置优先级
            .setPriority(Notification.PRIORITY_DEFAULT)
            //自定义震动效果
            .setFlags(Notification.FLAG_NO_CLEAR)
            //必须设置的属性，发送通知
            .sendNotification(9, "有新消息呢9", "这个是标题9", R.mipmap.ic_launcher)
    }


    private fun sendNotification10() {

        //处理点击Notification的逻辑
        //创建intent
        val resultIntent = Intent(this, TestActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)           //添加为栈顶Activity
        resultIntent.putExtra("what", 10)
        val resultPendingIntent = PendingIntent.getActivity(this, 10, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果

        val notificationUtils = NotificationUtils(this)
        notificationUtils
            //让通知左右滑的时候是否可以取消通知
            .setOngoing(true)
            .setContentIntent(resultPendingIntent)
            //设置状态栏的标题
            .setTicker("有新消息呢10")
            //设置自定义view通知栏布局
            .setContent(getRemoteViews())
            .setDefaults(Notification.DEFAULT_ALL)
            //设置sound
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
            //设置优先级
            .setPriority(Notification.PRIORITY_DEFAULT)
            //自定义震动效果
            .setFlags(Notification.FLAG_AUTO_CANCEL)
            //必须设置的属性，发送通知
            .sendNotification(10, "有新消息呢10", "这个是标题10", R.mipmap.ic_launcher)
    }


    private fun sendNotification11() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils
            .setOngoing(false)
            .setTicker("来通知消息啦")
            .setContent(getRemoteViews())
            //.setSound(Uri.parse("android.resource://com.yc.cn.ycnotification/" + R.raw.hah))
            .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "2"))
            .setPriority(Notification.PRIORITY_DEFAULT)
            .sendNotification(11, "我是伴有铃声效果的通知11", "美妙么?安静听~11", R.mipmap.ic_launcher)

    }


    private fun sendNotification12() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        val vibrate = longArrayOf(0, 500, 1000, 1500)
        //        Notification.Builder builder = new Notification.Builder(this)
        //                .setSmallIcon(R.mipmap.ic_launcher)
        //                .setContentTitle("我是伴有震动效果的通知")
        //                .setContentText("颤抖吧,逗比哈哈哈哈哈~")
        //                //使用系统默认的震动参数,会与自定义的冲突
        //                //.setDefaults(Notification.DEFAULT_VIBRATE)
        //                //自定义震动效果
        //                .setVibrate(vibrate);
        //        //另一种设置震动的方法
        //        //Notification notify = builder.build();
        //        //调用系统默认震动
        //        //notify.defaults = Notification.DEFAULT_VIBRATE;
        //        //调用自己设置的震动
        //        //notify.vibrate = vibrate;
        //        //mManager.notify(3,notify);
        //        mNotificationManager.notify(12, builder.build());

        val notificationUtils = NotificationUtils(this)
        notificationUtils
            .setPriority(Notification.PRIORITY_DEFAULT)
            .setVibrate(vibrate)
            .sendNotification(12, "我是伴有震动效果的通知", "颤抖吧,逗比哈哈哈哈哈~", R.mipmap.ic_launcher)

    }


    private fun sendNotification13() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils
            .setDefaults(Notification.DEFAULT_ALL)
            .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
            .sendNotification(13, "仔细看,我就执行一遍", "好了,已经一遍了~", R.mipmap.ic_launcher)

    }


    private fun sendNotification14() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils
            .setDefaults(Notification.DEFAULT_ALL)

            .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
            .sendNotification(14, "显示进度条14", "显示进度条内容，自定定义", R.mipmap.ic_launcher)
    }


    /**
     * 错误代码
     */
    private fun ssendNotification151() {
        val id = "channel_1"
        val description = "123"
        val importance = NotificationManager.IMPORTANCE_HIGH
        var mChannel: NotificationChannel? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = NotificationChannel(id, "123", importance)
            //  mChannel.setDescription(description);
            //  mChannel.enableLights(true);
            //  mChannel.setLightColor(Color.RED);
            //  mChannel.enableVibration(true);
            //  mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager?.createNotificationChannel(mChannel)
            val notification = Notification.Builder(this, id)
                .setContentTitle("Title")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentTitle("您有一条新通知")
                .setContentText("这是一条逗你玩的消息")
                .setAutoCancel(true)
                //                    .setContentIntent(pintent)
                .build()
            mNotificationManager?.notify(1, notification)
        }
    }


    private fun sendNotification15() {
        val notificationUtils = NotificationUtils(this)
        notificationUtils.sendNotification(15, "新消息来了", "周末到了，不用上班了", R.mipmap.ic_launcher)


    }

}
