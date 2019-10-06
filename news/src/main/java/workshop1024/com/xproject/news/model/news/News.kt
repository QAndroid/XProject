package workshop1024.com.xproject.news.model.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 发布者发布的新闻
 */
@Entity(tableName = "newses")
data class News(//新闻id
        @PrimaryKey @ColumnInfo(name = "saveId") var mSaveId: String,//内存和本地存储News的id
        @ColumnInfo(name = "newsId") var mNewsId: String, //新闻属于的发布者
        @ColumnInfo(name = "subscribeId") var mSubscribeId: String,//新闻属于的过滤器id
        @ColumnInfo(name = "filterIdList") var mFilterIdList: List<String>,//新闻属于tag的id
        @ColumnInfo(name = "tagIdList") var mTagIdList: List<String>,//新闻头图Url
        @ColumnInfo(name = "bannerUrl") var mBannerUrl: String, //新闻标题
        @ColumnInfo(name = "title") var mTitle: String, //新闻发布者
        @ColumnInfo(name = "publisher") var mPublisher: String, //新闻发布时间
        @ColumnInfo(name = "pubDate") var mPubDate: String,//新闻是否已阅读
        @ColumnInfo(name = "isReaded") var mIsReaded: Boolean, //新闻是否已保存
        @ColumnInfo(name = "isSaved") var mIsSaved: Boolean,//搜索类型
        @ColumnInfo(name = "searchType") var mSearchType: String, //搜索关键字
        @ColumnInfo(name = "searchKey") var mSearchKey: String) {

    companion object {
        const val SUBSCRIBE_TYPE: String = "1"
        const val TAG_TYPE: String = "2"
        const val FILTER_TYPE: String = "3"
        const val SEARCH_TYPE: String = "4"
        const val SAVED_TYPE: String = "5"
    }
}
