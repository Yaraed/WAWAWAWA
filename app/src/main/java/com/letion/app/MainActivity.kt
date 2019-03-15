package com.letion.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.*
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.blankj.utilcode.util.ServiceUtils
import com.letion.a_ble.BleActivity
import com.letion.app.di.component.DaggerMainComponent
import com.letion.app.di.module.MainModule
import com.letion.app.glide.Glide4Engine
import com.letion.app.receiver.OnePixelReceiver
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.possupport.arch.RxLiftUtils
import com.weyee.possupport.callback.Callback
import com.weyee.sdk.api.rxutil.RxJavaUtils
import com.weyee.sdk.dialog.QMUIBottomSheet
import com.weyee.sdk.event.Bus
import com.weyee.sdk.event.NormalEvent
import com.weyee.sdk.router.*
import com.weyee.sdk.toast.ToastUtils
import com.weyee.sdk.util.sp.SpUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_main.*
import me.leolin.shortcutbadger.ShortcutBadger
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.*

class MainActivity : BaseActivity<MainPresenter>(), MainContract.MainView {

    private val CHOOSE = 1
    //private lateinit var presenter: MainPresenter
    private var dialog: LoadingDialog? = null
    private var onePixelReceiver: BroadcastReceiver? = null
    private lateinit var connection: ServiceConnection
    private var isBindService: Boolean = false

    /**
     * 如果initView返回0,框架则不会调用[android.app.Activity.setContentView]
     *
     * @return
     */
    override fun getResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        //tvContent.text = printJson(parseJson(readJson()))
//        Thread {
//            while (true) {
//                ShortcutBadger.applyCount(applicationContext, (Math.random() * 1000).toInt())
//                Thread.sleep(2000)
//            }
//        }.start()

        headerView.isShowMenuLeftBackView(false)

        RxJavaUtils.polling(2).`as`(RxLiftUtils.bindLifecycle(this@MainActivity)).subscribe {
            ShortcutBadger.applyCount(applicationContext, (Math.random() * 1000).toInt())
        }

        //presenter = MainPresenter(this)

        val array = arrayOfNulls<String>(31)
        for (i in 0 until 31) {
            array[i] = "这是第${i}个"
        }

        val urls = arrayOf(
            "http://img.weyee.com/weyee_score_2016_20180425151306753282",
            "http://img.weyee.com/weyee_score_2016_20180425151307495086",
            "http://img.weyee.com/weyee_score_2016_20180906104526564400"
        )

        listView.adapter = ArrayAdapter<String>(baseContext, android.R.layout.simple_list_item_1, array)

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            run {
                when (i) {
                    0 -> App.obtainAppComponentFromContext(this@MainActivity).navigation().obtainNavigation(
                        MainNavigation::class.java
                    ).toPhotoViewActivity(urls)
                    1 -> mPresenter.getBook()
                    2 -> mPresenter.cancelBook()
                    3 -> mPresenter.getBook(true)
                    4 -> mPresenter.cancelBook(true)
                    5 -> mPresenter.downloadApk()
                    6 -> mPresenter.cancelApk()
                    7 -> toPhoto(9)
                    8 -> toBasic(array[i])
                    9 -> toGrid(array[i])
                    10 -> MainNavigation(this@MainActivity).toTranslucentActivity(1)
                    11 -> IntentNavigation(this@MainActivity).toIntentActivity()
                    12 -> IntentNavigation(this@MainActivity).toOtherActivity()
                    13 -> IntentNavigation(this@MainActivity).toPermissionActivity()
                    14 -> HttpNavigation(this@MainActivity).toWebSocketActivity()
                    15 -> BlurDialog(this@MainActivity).show()
                    16 -> HttpNavigation(this@MainActivity).toDaemonActivity()
                    17 -> BleNavigation(this@MainActivity).toFastBleActivity()
                    18 -> MainNavigation(this@MainActivity).toImageViewActivity()
                    19 -> MainNavigation(this@MainActivity).toSpinnerActivity()
                    20 -> HttpNavigation(this@MainActivity).toTestActivity()
                    21 -> GPUNavigation(this@MainActivity).toGPUActivity()
                    22 -> GPUNavigation(this@MainActivity).toVideoActivity()
                    23 -> GPUNavigation(this@MainActivity).toAudioActivity()
                    24 -> MainNavigation(this@MainActivity).toPreViewActivity()
                    25 -> PdfNavigation(this@MainActivity).toPdfActivity()
                    26 -> PdfNavigation(this@MainActivity).toCanvasActivity()
                    27 -> PdfNavigation(this@MainActivity).toZoomActivity()
                    28 -> PdfNavigation(this@MainActivity).toClickableActivity()
                    29 -> {
                        SpUtils.getDefault().put("10086", "10086")
                        ToastUtils.show(SpUtils.getDefault().getInt("10086"))

                        //SpUtils.getDefault().clear()
                    }
                    30 -> {
                        GPUNavigation(this@MainActivity).toMediaActivity()
                    }
                    else -> {
                        Bus.getDefault().post(NormalEvent())
                    }
                }
            }
        }

        Bus.getDefault().subscribe(this, "RxBus", null, com.weyee.sdk.event.Callback<NormalEvent> {
            println("RxBus")
            setAlias1()
        })

        connection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is BindService.DefaultBinder) {
                    service.start()
                } else if (service != null) {
                    val inter = IMyAidlInterface.Stub.asInterface(service)
                    println(inter.plus(100, 200))
                    inter.start()
                }
            }

        }

        ServiceUtils.startService(BindService::class.java)
        ServiceUtils.bindService(BindService::class.java, connection, Context.BIND_AUTO_CREATE)

        if (Build.VERSION.SDK_INT > 23) {
            ToastUtils.show(if (Settings.canDrawOverlays(this)) "已获得悬浮窗权限" else "暂无悬浮窗权限")
        }
        ServiceUtils.startService(SuspensionService::class.java)
    }

    override fun initData(savedInstanceState: Bundle?) {
        onePixelReceiver = OnePixelReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.SCREEN_OFF")
        intentFilter.addAction("android.intent.action.SCREEN_ON")
        intentFilter.addAction("android.intent.action.USER_PRESENT")
        registerReceiver(onePixelReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onePixelReceiver)
        ServiceUtils.stopService(BindService::class.java)
        ServiceUtils.unbindService(connection)
        ServiceUtils.stopService(SuspensionService::class.java)
    }

    override fun canSwipeBack(): Boolean {
        return !super.canSwipeBack()
    }

    override fun useEventBus(): Boolean {
        return !super.useEventBus()
    }

    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     *
     * @param appComponent
     */
    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerMainComponent
            .builder()
            .appComponent(appComponent)
            .mainModule(MainModule(this))
            .build()
            .inject(this@MainActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (CHOOSE === requestCode && resultCode === Activity.RESULT_OK) {
            val selectedList = Matisse.obtainResult(data)
            val paths = mutableListOf<String>()
            for (i in 0 until selectedList.size) {
                paths.add(Glide4Engine.getRealPathFromURI(this, selectedList[i]))
            }
            mPresenter.uploadImages(paths)
        }
    }

    @SuppressLint("CheckResult")
    private fun toPhoto(maxSelectable: Int) {
        Matisse.from(this@MainActivity)
            .choose(MimeType.allOf())
            .countable(true)
            .maxSelectable(maxSelectable)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(Glide4Engine())
            .forResult(CHOOSE)
    }

    private fun toBasic(msg: String?) {
        QMUIBottomSheet.BottomListSheetBuilder(this@MainActivity, true)
            .setCheckedIndex(2)
            .setTitle(msg)
            .addItem("Item 1")
            .addItem("Item 2")
            .addItem("Item 3")
            .setOnSheetItemClickListener { dialog, _, _, tag ->
                run {
                    dialog.dismiss()
                    ToastUtils.show(tag)
                }
            }
            .build().show()
    }

    private fun toGrid(msg: String?) {
        QMUIBottomSheet.BottomGridSheetBuilder(this@MainActivity)
            .addItem(
                R.mipmap.icon_more_operation_share_friend,
                "分享到微信",
                0,
                QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE
            )
            .addItem(
                R.mipmap.icon_more_operation_share_moment,
                "分享到朋友圈",
                1,
                QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE
            )
            .addItem(
                R.mipmap.icon_more_operation_share_chat,
                "分享到QQ",
                2,
                QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE
            )
            .addItem(
                R.mipmap.icon_more_operation_share_chat,
                "分享到空间",
                3,
                QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE
            )
            .addItem(
                R.mipmap.icon_more_operation_share_weibo,
                "分享到微博",
                4,
                QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE
            )
            .addItem(
                R.mipmap.icon_more_operation_share_chat,
                "分享到私信",
                5,
                QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE
            )
            .addItem(R.mipmap.icon_more_operation_save, "保存到本地", 6, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
            .setOnSheetItemClickListener { dialog, itemView ->
                run {
                    dialog.dismiss()
                    val tag = itemView.tag
                    when (tag) {
                        0 -> ToastUtils.show("分享到微信")
                        1 -> ToastUtils.show("分享到朋友圈")
                        2 -> ToastUtils.show("分享到QQ")
                        3 -> ToastUtils.show("分享到空间")
                        4 -> ToastUtils.show("分享到微博")
                        5 -> ToastUtils.show("分享到私信")
                        6 -> ToastUtils.show("保存到本地")
                    }
                }
            }
            .build().show()
    }

    fun readJson(): String {
        val inputStream = assets.open("emoji.json")
        val length = inputStream.available()
        val byteArray = ByteArray(length)
        inputStream.read(byteArray)
        inputStream.close()
        return String(byteArray, Charset.defaultCharset())
    }

    fun parseJson(json: String): List<Emoji> {
        val list = ArrayList<Emoji>()
        val jsonArray = JSONObject(json).optJSONArray("list")
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.optJSONObject(i)
                list.add(Emoji(jsonObject?.optString("name"), jsonObject.optInt("unicode")))
            }
        }
        return list
    }

    fun printJson(array: List<Emoji>): String {
        val builder = StringBuilder()
        array.forEach {
            builder.append(it.unicodeTemp)
        }
        return builder.toString()
    }

    data class Emoji(var name: String?, var unicode: Int) {
        val unicodeTemp: String?
            get() = String(Character.toChars(unicode))
    }

    override fun dialog(): Dialog? {
        if (dialog == null) {
            dialog = LoadingDialog(this, "加载中...")
        }
        return dialog
    }

    override fun context(): Context {
        return baseContext
    }

    override fun showProgress(progress: Int) {
        runOnUiThread {
            if (dialog is ProgressDialog) {
                (dialog as ProgressDialog).progress = progress
            }
        }
    }

    @SuppressLint("WrongConstant")
            /**
             * 设置别名和图标
             */
    fun setAlias1() {
        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$packageName.MainActivity1"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$packageName.MainActivity"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        val callback = Callback<Any> { it ->
            it.iterator().forEach {
                print(it?.javaClass?.canonicalName)
            }
        }


        callback.call(1, packageManager, context(), null, "any")


        startActivity(Intent(this, BleActivity::class.java))
    }
}
