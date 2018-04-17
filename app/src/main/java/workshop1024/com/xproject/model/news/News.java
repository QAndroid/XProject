package workshop1024.com.xproject.model.news;

/**
 * 发布者发布的新闻
 */
public class News {
    //新闻id
    private String mNewId;
    //新闻头图Url
    private String mBannerUrl;
    //新闻标题
    private String mTitle;
    //新闻发布者
    private String mPublisher;
    //新闻发布时间
    private String mPubDate;

    public News(String newId, String bannerUrl, String title, String publisher, String pubDate) {
        mNewId = newId;
        mBannerUrl = bannerUrl;
        mTitle = title;
        mPublisher = publisher;
        mPubDate = pubDate;
    }

    public String getNewId() {
        return mNewId;
    }

    public void setNewId(String newId) {
        mNewId = newId;
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

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

}
