package workshop1024.com.xproject.model.subscribe;

/**
 * 已订阅发布者
 */
public class Subscribe {
    //已订阅发布者的id
    private String mSubscribeId;

    //发布者图标URL
    private String mIconUrl;
    //发布者名称
    private String mName;
    //发布者自定义名称
    private String mCustomName;
    //发布者未阅读新闻数量
    private String mUnreadCount;
    //发布者是否被订阅
    private boolean mIsSubscribed;

    /**
     * 构造函数
     *
     * @param subscribeId  已订阅发布者id
     * @param iconUrl      已订阅发布者图标URL
     * @param name         已订阅发布者名称
     * @param customName   已订阅发布者自定义名称
     * @param unreadCount  已订阅发布者未阅读新闻数量
     * @param isSubscribed 已订阅发布者是否订阅
     */
    public Subscribe(String subscribeId, String iconUrl, String name, String customName, String
            unreadCount, boolean isSubscribed) {
        mSubscribeId = subscribeId;
        mIconUrl = iconUrl;
        mName = name;
        mCustomName = customName;
        mUnreadCount = unreadCount;
        mIsSubscribed = isSubscribed;
    }

    public String getSubscribeId() {
        return mSubscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        mSubscribeId = subscribeId;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = mName;
    }

    public String getCustomName() {
        return mCustomName;
    }

    public void setCustomName(String customName) {
        mCustomName = customName;
    }

    public String getUnreadCount() {
        return mUnreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        mUnreadCount = unreadCount;
    }

    public boolean isSubscribed() {
        return mIsSubscribed;
    }

    public void setIsSubscribed(boolean isSubscribed) {
        mIsSubscribed = isSubscribed;
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "mSubscribeId='" + mSubscribeId + '\'' +
                ", mIconUrl='" + mIconUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mCustomName='" + mCustomName + '\'' +
                ", mUnreadCount='" + mUnreadCount + '\'' +
                ", mIsSubscribed=" + mIsSubscribed +
                '}';
    }
}
