package workshop1024.com.xproject.news.model.newsdetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//FIXME 不能使用data class类
/**
 * 发布者发布的新闻详情
 */
@Entity(tableName = "newsdetails")
class NewsDetail(
        @PrimaryKey @ColumnInfo(name = "newsId") var mNewsId: String,//新闻id
        @ColumnInfo(name = "bannerUrl") var mBannerUrl: String,//头图url
        @ColumnInfo(name = "title") var mTitle: String,//新闻标题
        @ColumnInfo(name = "publisher") var mPublisher: String,//发布者
        @ColumnInfo(name = "pubDate") var mPubDate: String,//新闻作者
        @ColumnInfo(name = "author") var mAuthor: String?, //新闻的内容
        @ColumnInfo(name = "content") var mContent: String?, //新闻的标签
        @ColumnInfo(name = "tagList") var mTagList: List<String>?,//是否已读
        @ColumnInfo(name = "isReaded") var mIsReaded: Boolean,//是否保存
        @ColumnInfo(name = "isSaved") var mIsSaved: Boolean)
