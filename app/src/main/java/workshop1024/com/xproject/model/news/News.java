package workshop1024.com.xproject.model.news;


import java.util.List;

/**
 * 发布者发布的新闻
 */
public class News {
    //新闻id
    private String mNewId;
    //新闻发布者的id
    private String mPublisherId;
    //新闻头图Url
    private String mBannerUrl;
    //新闻标题
    private String mTitle;
    //新闻作者
    private String mAuthor;
    //新闻发布时间
    private String mPubDate;
    //新闻标志id集合
    private List<String> mTagList;
    //新闻是否已读
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
