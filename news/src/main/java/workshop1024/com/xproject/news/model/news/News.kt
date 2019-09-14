package workshop1024.com.xproject.news.model.news

/**
 * 发布者发布的新闻
 */
open class News(//新闻id
        var newId: String?, //新闻头图Url
        var bannerUrl: String?, //新闻标题
        var title: String?, //新闻发布者
        var publisher: String?, //新闻发布时间
        var pubDate: String?,
        //新闻是否已阅读
        var isIsReaded: Boolean, //新闻是否已保存
        var isIsSaved: Boolean)
