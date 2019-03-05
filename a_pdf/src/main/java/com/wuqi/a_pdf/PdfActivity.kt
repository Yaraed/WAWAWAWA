/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.wuqi.a_pdf

import android.os.Bundle
import android.text.DynamicLayout
import android.text.Layout
import android.text.TextPaint
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.router.PdfNavigation
import com.weyee.sdk.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_pdf.*


@Route(path = PdfNavigation.MODULE_NAME + "Pdf")
class PdfActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {
    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun getResourceId(): Int = R.layout.activity_pdf

    override fun initView(savedInstanceState: Bundle?) {
        pdfView.fromAsset("Java面试突击第一版.pdf")
            //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            // allows to draw something on the current page, usually visible in the middle of the screen
            .onDraw(null)
            // allows to draw something on all pages, separately for every page. Called only for visible pages
            .onDrawAll { canvas, pageWidth, pageHeight, _ ->
                val textPaint = TextPaint()
                textPaint.setARGB(0x80, 0, 0, 0)//设置水印颜色
                textPaint.textSize = 45.0f//设置水印字体大小
                textPaint.isAntiAlias = true // 抗锯齿
                //参数意义分别为：文字内容、TextPaint对象、文本宽度、对齐方式、行距倍数、行距加数和是否包含内边距。
                //这里比较重要的地方是设置文本宽度，当文本宽度比这个值大的时候就会自动换行。
                val layout = DynamicLayout(
                    "这是水印", textPaint, pageWidth.toInt(),
                    Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true
                )
                canvas.save()
                canvas.translate(20f, pageHeight * 7 / 8)
                canvas.rotate(15f)
                layout.draw(canvas)
                canvas.restore()
            }
            .onLoad(null) // called after document is loaded and starts to be rendered
            .onPageChange { page, pageCount ->
                ToastUtils.show("$page/$pageCount")
            }
            .onPageScroll(null)
            .onError(null)
            .onPageError(null)
            .onRender(null) // called after document is rendered for the first time
            // called on single tap, return true if handled, false to toggle scroll handle visibility
            .onTap(null)
            .onLongPress(null)
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
            .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
            .linkHandler(DefaultLinkHandler(pdfView))
            .pageFitPolicy(FitPolicy.WIDTH)
            .pageSnap(true) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load()
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}
