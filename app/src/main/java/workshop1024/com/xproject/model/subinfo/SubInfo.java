package workshop1024.com.xproject.model.subinfo;

public class SubInfo {
    //Info的id
    private String mInfoId;

    //Info图标URL
    private String mIconUrl;
    //Info名称
    private String mName;
    //Info未阅读新闻数量
    private String mUnreadCount;

    public SubInfo(String infoId, String iconUrl, String name, String unreadCount) {
        mInfoId = infoId;
        mIconUrl = iconUrl;
        mName = name;
        mUnreadCount = unreadCount;
    }

    public String getInfoId() {
        return mInfoId;
    }

    public void setInfoId(String infoId) {
        mInfoId = infoId;
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
        mName = name;
    }

    public String getUnreadCount() {
        return mUnreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        mUnreadCount = unreadCount;
    }

    @Override
    public String toString() {
        return "SubInfo{" +
                "mInfoId='" + mInfoId + '\'' +
                ", mIconUrl='" + mIconUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mUnreadCount='" + mUnreadCount + '\'' +
                '}';
    }
}
