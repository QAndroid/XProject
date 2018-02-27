package workshop1024.com.xproject.model.publisher;

/**
 * 发布者数据类
 */
public class Publisher {
    //发布者id
    private String id;
    //发布者类型
    private String type;
    //发布者语言类型
    private String language;

    //发布者图标URL
    private String iconUrl;
    //发布者名称
    private String name;
    //发布者自定义名称
    private String reName;
    //发布者订阅数量
    private String subscribeNum;
    //发布者是否被订阅
    private boolean isSubscribed;

    //发布者订阅头图URL
    private String bannerUrl;
    //发布者新消息数量
    private String newsCount;

    public Publisher(String id, String type, String language, String iconUrl, String name, String subscribeNum,
                     boolean isSubscribed, String bannerUrl, String newsCount) {
        this.id = id;
        this.type = type;
        this.language = language;
        this.iconUrl = iconUrl;
        this.name = name;
        this.subscribeNum = subscribeNum;
        this.isSubscribed = isSubscribed;
        this.bannerUrl = bannerUrl;
        this.newsCount = newsCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscribeNum() {
        return subscribeNum;
    }

    public String getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String geticonUrl() {
        return iconUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getNewsCount() {
        return newsCount;
    }
}
