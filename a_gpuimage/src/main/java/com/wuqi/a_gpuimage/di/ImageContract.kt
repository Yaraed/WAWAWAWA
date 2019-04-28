package com.wuqi.a_gpuimage.di

import com.weyee.poscore.mvp.IModel
import com.weyee.poscore.mvp.IView
import io.reactivex.Observable

/**
 *
 * @author wuqi by 2019/4/12.
 */
interface ImageContract {
    interface ImageView : IView {
        fun setImages(bean: List<String>?)

        fun onCompleted()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getImages(pageSize: Int, pageIndex: Int): Observable<List<String>>
    }

}