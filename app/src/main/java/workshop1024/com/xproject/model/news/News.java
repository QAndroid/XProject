package workshop1024.com.xproject.model.news;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

import workshop1024.com.xproject.model.publisher.Publisher;

/**
 * 发布者发布的新闻
 */
@Entity(tableName = "news")
public class News {
    //新闻id
    @PrimaryKey
    @NonNull
    private String mNewId;
    //新闻发布者的id
    @ColumnInfo(name = "publisherId")
    private String mPublisherId;
    //新闻头图Url
    @ColumnInfo(name = "bannerUrl")
    private String mBannerUrl;
    //新闻标题
    @ColumnInfo(name = "title")
    private String mTitle;
    //新闻作者
    @ColumnInfo(name = "author")
    private String mAuthor;
    //新闻发布时间
    @ColumnInfo(name = "pubDate")
    private String mPubDate;
    //新闻标志id集合
    @ColumnInfo(name = "tagList")
    private List<String> mTagList;
    //新闻是否已读
    @ColumnInfo(name = "isReaded")
    private Boolean mIsReaded;


    public News(String newId, String publisherId, String bannerUrl, String title, String author,
                String pubDate, List<String> tagList, Boolean isReaded) {
        mNewId = newId;
        mPublisherId = publisherId;
        mBannerUrl = bannerUrl;
        mTitle = title;
        mAuthor = author;
        mPubDate = pubDate;
        mTagList = tagList;
        mIsReaded = isReaded;
    }

    public String getNewId() {
        return mNewId;
    }

    public void setNewId(String newId) {
        mNewId = newId;
    }

    public String getPublisherId() {
        return mPublisherId;
    }

    public void setPublisherId(String publisherId) {
        this.mPublisherId = publisherId;
    }

    public String getBannerUrl() {
        return mBannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        mBannerUrl = bannerUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public List<String> getTagList() {
        return mTagList;
    }

    public void setTagList(List<String> tagList) {
        mTagList = tagList;
    }

    public Boolean getIsReaded() {
        return mIsReaded;
    }

    public void setIsReaded(Boolean isReaded) {
        mIsReaded = isReaded;
    }
}
