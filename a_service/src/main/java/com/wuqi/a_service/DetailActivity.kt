package com.wuqi.a_service

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ScreenUtils
import com.weyee.poscore.base.App
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.api.rxutil.RxJavaUtils
import com.weyee.sdk.api.rxutil.task.RxAsyncTask
import com.weyee.sdk.imageloader.glide.GlideImageConfig
import com.weyee.sdk.imageloader.progress.OnRequestListener
import com.weyee.sdk.router.MainNavigation
import com.weyee.sdk.router.Path
import com.weyee.sdk.util.htmlmark.HtmlImageLoader
import com.weyee.sdk.util.htmlmark.HtmlMark
import kotlinx.android.synthetic.main.activity_detail.*
import java.net.URL

@Route(path = Path.Service + "Detail")
class DetailActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
        val url = intent.getStringExtra("url")
        showProgress()
        RxJavaUtils.executeAsyncTask(object : RxAsyncTask<String, String>(url) {
            override fun doInUIThread(t: String?) {
                hideProgress()
                /**
                 * 卧槽，超蛋疼，千万别用该方法去加载HTML，加载图片本身就很慢，加上正则，图片计算，tag标签解析，性能太差
                 */
                HtmlMark.from(t, object : HtmlImageLoader {
                    override fun loadImage(url: String?, callback: HtmlImageLoader.Callback?) {
                        (applicationContext as App).appComponent.imageLoader().loadImage(
                            context, GlideImageConfig
                                .builder().resource(url)
                                .listener(object : OnRequestListener<BitmapDrawable> {
                                    override fun onLoadFailed(): Boolean {
                                        callback?.onLoadFailed()
                                        return true
                                    }

                                    override fun onResourceReady(resource: BitmapDrawable?): Boolean {
                                        callback?.onLoadComplete(resource?.bitmap)
                                        return true
                                    }

                                })
                                .build()
                        )
                    }

                    override fun getDefaultDrawable(): Int = R.mipmap.ic_back

                    override fun getErrorDrawable(): Int = R.mipmap.ic_back

                    override fun getMaxWidth(): Int =
                        ScreenUtils.getScreenWidth() - tvContent.paddingLeft - tvContent.paddingRight

                    override fun fitWidth(): Boolean = true

                    override fun imageClick(index: Int, urls: MutableList<String>?) {
                        MainNavigation(context).toPhotoViewActivity(index, urls?.toTypedArray())
                    }

                }).into(tvContent)
            }

            override fun doInIOThread(t: String?): String? {
                //return "<p style=\"text-align: center; line-height: 1.75em;\"><img src=\"http://filezyc.qbbss.com/201712/354025b9aa424c9fb3a4f76ae48f3ae1.jpg\" title=\"1.jpg\" alt=\"1.jpg\"/></p><p style=\"text-indent: 2em; margin-top: 15px; margin-bottom: 15px; line-height: 3em;\"><span style=\"font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 18px; color: rgb(38, 38, 38);\">为落实《国务院关于调整工业产品生产许可证管理目录和试行简化审批程序的决定》的有关要求，加强对取消生产许可证管理产品的事中事后监管，2017年8月26日至27日，产品质量监督司组织了来自13家检验机构的25名技术专家，研究制定了16类取消生产许可证管理产品的国家监督抽查实施方案并进行研讨审核，对抽查实施方案逐一进行了核查，针对重点问题共同研究探讨拿出解决办法，对各方案提出了修改完善的建议。</span></p><p style=\"text-indent: 2em; margin-top: 15px; margin-bottom: 15px; line-height: 3em;\"><span style=\"font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 18px; color: rgb(38, 38, 38);\">产品质量监督司张文兵司长、孙会川副司长及各处处长参加评审。张司长强调，要高度重视本次抽查工作，对所有原获证企业全覆盖，全面考虑抽查工作细节，科学合理制定抽查方案内容，全力保障本次抽查工作顺利完成。下一步，产品质量监督司将组织开展检验机构抽查任务申报审核工作，进行检验机构“双随机”遴选，尽快部署开展抽查。</span></p><p><br/></p>"
                return URL(t).readText()
            }

        })
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun useProgressAble(): Boolean {
        return !super.useProgressAble()
    }
}
