package workshop1024.com.xproject.home.model.news

//FIXME 不能使用data class类
/**
 * 发布者发布的新闻详情
 */
class NewsDetail(newId: String, bannerUrl: String, title: String, publisher: String, pubDate: String,
        //新闻作者
                 var author: String?, //新闻的内容
                 var content: String?, //新闻的标签
                 var tagList: List<String>?, isReaded: Boolean, isSaved: Boolean) : News(newId, bannerUrl, title, publisher, pubDate, isReaded, isSaved)
