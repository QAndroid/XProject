package workshop1024.com.xproject.model.sub.subscribe;

import workshop1024.com.xproject.model.sub.SubInfo;

/**
 * 已订阅发布者
 */
public class Subscribe extends SubInfo {
    //发布者自定义名称
    private String mCustomName;
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
        super(subscribeId, iconUrl, name, unreadCount);
        mCustomName = customName;
        mIsSubscribed = isSubscribed;
    }

    public String getCustomName() {
        return mCustomName;
    }

    public void setCustomName(String customName) {
        mCustomName = customName;
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
                "mCustomName='" + mCustomName + '\'' +
                ", mIsSubscribed=" + mIsSubscribed +
                '}';
    }
}
