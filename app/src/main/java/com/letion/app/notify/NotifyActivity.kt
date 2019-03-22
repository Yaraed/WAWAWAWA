package com.letion.app.notify

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
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
import com.weyee.sdk.util.notify.NotifyUtils
import com.wuqi.a_intent.OtherActivity
import kotlinx.android.synthetic.main.activity_notify.*
import java.util.*

@Route(path = Path.MAIN + "Notify")
class NotifyActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    private val requestCode = SystemClock.uptimeMillis().toInt()
    private var currentNotify: NotifyUtils? = null

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_notify

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this@NotifyActivity)

        recyclerView.adapter = object : BaseAdapter<String>(null,
            OnRecyclerViewItemClickListener<String> { _, _, _, position ->
                when (position) {
                    0 -> notify_normal_singLine()
                    1 -> notify_normal_moreLine()
                    2 -> notify_mailbox()
                    3 -> notify_bigPic()
                    4 -> notify_customview()
                    5 -> notify_buttom()
                    6 -> notify_progress()
                    7 -> notify_headUp()
                    8 -> currentNotify?.clear()
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
        val data = resources.getStringArray(R.array.notify_entry).asList()
        (recyclerView.adapter as BaseAdapter<String>).addAll(data)
    }

    /**
     * 高仿淘宝
     */
    private fun notify_normal_singLine() {
        //设置想要展示的数据内容
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val smallIcon = R.drawable.tb_bigicon
        val ticker = "您有一条新通知"
        val title = "双十一大优惠！！！"
        val content = "仿真皮肤充气娃娃，女朋友带回家！"

        //实例化工具类，并且调用接口
        val notify1 = NotifyUtils(context, 1)
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false)
        currentNotify = notify1
    }

    /**
     * 高仿网易新闻
     */
    private fun notify_normal_moreLine() {
        //设置想要展示的数据内容
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val smallIcon = R.drawable.netease_bigicon
        val ticker = "您有一条新通知"
        val title = "朱立伦请辞国民党主席 副主席黄敏惠暂代党主席"
        val content = "据台湾“中央社”报道，国民党主席朱立伦今天(18日)向中常会报告，为败选请辞党主席一职，他感谢各位中常委的指教包容，也宣布未来党务工作由副主席黄敏惠暂代，完成未来所有补选工作。"
        //实例化工具类，并且调用接口
        val notify2 = NotifyUtils(context, 2)
        notify2.notify_normail_moreline(pIntent, smallIcon, ticker, title, content, true, true, false)
        currentNotify = notify2
    }

    /**
     * 收件箱样式
     */
    private fun notify_mailbox() {
        //设置想要展示的数据内容
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val largeIcon = R.drawable.fbb_largeicon
        val smallIcon = R.drawable.wx_smallicon
        val ticker = "您有一条新通知"
        val title = "冰冰"
        val messageList = ArrayList<String>()
        messageList.add("文明,今晚有空吗？")
        messageList.add("晚上跟我一起去玩吧?")
        messageList.add("怎么不回复我？？我生气了！！")
        messageList.add("我真生气了！！！！！你听见了吗!")
        messageList.add("文明，别不理我！！！")
        val content = "[" + messageList.size + "条]" + title + ": " + messageList[0]
        //实例化工具类，并且调用接口
        val notify3 = NotifyUtils(context, 3)
        notify3.notify_mailbox(
            pIntent, smallIcon, largeIcon, messageList, ticker,
            title, content, true, true, false
        )
        currentNotify = notify3
    }

    /**
     * 高仿系统截图通知
     */
    private fun notify_bigPic() {
        //设置想要展示的数据内容
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val smallIcon = R.drawable.xc_smaillicon
        val largePic = R.drawable.screenshot
        val ticker = "您有一条新通知"
        val title = "已经抓取屏幕截图"
        val content = "触摸可查看您的屏幕截图"
        //实例化工具类，并且调用接口
        val notify4 = NotifyUtils(context, 4)
        notify4.notify_bigPic(pIntent, smallIcon, ticker, title, content, largePic, true, true, false)
        currentNotify = notify4
    }


    /**
     * 高仿应用宝
     */
    private fun notify_customview() {
        //设置想要展示的数据内容
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val ticker = "您有一条新通知"

        //设置自定义布局中按钮的跳转界面
        val btnIntent = Intent(context, OtherActivity::class.java)
        btnIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        //如果是启动activity，那么就用PendingIntent.getActivity，如果是启动服务，那么是getService
        val Pintent = PendingIntent.getActivity(
            context,
            SystemClock.uptimeMillis().toInt(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 自定义布局
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.notify_yyb
        )
        remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon)
        remoteViews.setTextViewText(R.id.title, "垃圾安装包太多")
        remoteViews.setTextViewText(R.id.text, "3个无用安装包，清理释放的空间")
        remoteViews.setOnClickPendingIntent(R.id.button, Pintent)//定义按钮点击后的动作
        val smallIcon = R.drawable.yybao_smaillicon
        //实例化工具类，并且调用接口
        val notify5 = NotifyUtils(context, 5)
        notify5.notify_customview(remoteViews, pIntent, smallIcon, ticker, true, true, false)
        currentNotify = notify5
    }

    /**
     * 高仿Android更新提醒样式
     */
    private fun notify_buttom() {
        //设置想要展示的数据内容
        val ticker = "您有一条新通知"
        val smallIcon = R.drawable.android_bigicon
        val lefticon = R.drawable.android_leftbutton
        val lefttext = "以后再说"
        val leftIntent = Intent()
        leftIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val leftPendIntent = PendingIntent.getActivity(
            context,
            requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val righticon = R.drawable.android_rightbutton
        val righttext = "安装"
        val rightIntent = Intent(context, OtherActivity::class.java)
        rightIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val rightPendIntent = PendingIntent.getActivity(
            context,
            requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        //实例化工具类，并且调用接口
        val notify6 = NotifyUtils(context, 6)
        notify6.notify_button(
            smallIcon,
            lefticon,
            lefttext,
            leftPendIntent,
            righticon,
            righttext,
            rightPendIntent,
            ticker,
            "系统更新已下载完毕",
            "Android 6.0.1",
            true,
            true,
            false
        )
        currentNotify = notify6
    }


    /**
     * 高仿Android系统下载样式
     */
    private fun notify_progress() {
        //设置想要展示的数据内容
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val rightPendIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val smallIcon = R.drawable.android_bigicon
        val ticker = "您有一条新通知"
        //实例化工具类，并且调用接口
        val notify7 = NotifyUtils(context, 7)
        notify7.notify_progress(rightPendIntent, smallIcon, ticker, "Android 6.0.1 下载", "正在下载中", true, false, false)
        currentNotify = notify7
    }

    /**
     * Android 5。0 新特性：悬浮式通知
     */
    private fun notify_headUp() {
        //设置想要展示的数据内容
        val smallIcon = R.drawable.hl_smallicon
        val largeIcon = R.drawable.fbb_largeicon
        val ticker = "您有一条新通知"
        val title = "范冰冰"
        val content = "文明，今晚在希尔顿酒店2016号房哈"
        val intent = Intent(context, OtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )


        val lefticon = R.drawable.hl_message
        val lefttext = "回复"
        val leftIntent = Intent()
        leftIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val leftPendingIntent = PendingIntent.getActivity(
            context,
            requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val righticon = R.drawable.hl_call
        val righttext = "拨打"
        val rightIntent = Intent(context, OtherActivity::class.java)
        rightIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val rightPendingIntent = PendingIntent.getActivity(
            context,
            requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        //实例化工具类，并且调用接口
        val notify8 = NotifyUtils(context, 8)
        notify8.notify_HeadUp(
            pendingIntent,
            smallIcon,
            largeIcon,
            ticker,
            title,
            content,
            lefticon,
            lefttext,
            leftPendingIntent,
            righticon,
            righttext,
            rightPendingIntent,
            true,
            true,
            false
        )
        currentNotify = notify8
    }
}
