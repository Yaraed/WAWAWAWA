package com.wuqi.a_service.wan

/**
 *
 * @author wuqi by 2019/4/17.
 */
data class ArticleBean(
    val curPage: Int,
    val datas: List<ArticleBeanData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ArticleBeanData(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<ArticleBeanTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class ArticleBeanTag(
    val name: String,
    val url: String
)