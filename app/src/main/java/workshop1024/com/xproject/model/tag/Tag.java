package workshop1024.com.xproject.model.tag;

/**
 * 发布者新闻Tag
 */
public class Tag {
    //Tag的id
    private String mTagId;
    //Tag图标URL
    private String mIconUrl;
    //Tag名称
    private String mName;
    //Tag未阅读新闻数量
    private String mUnreadCount;

    /**
     * 构造函数
     *
     * @param tagId       Tag的id
     * @param iconUrl     Tag图表URL
     * @param name        Tag名称
     * @param unreadCount Tag未阅读新闻数量
     */
    public Tag(String tagId, String iconUrl, String name, String unreadCount) {
        mTagId = tagId;
        mIconUrl = iconUrl;
        mName = name;
        mUnreadCount = unreadCount;
    }

    public String getTagId() {
        return mTagId;
    }

    public void setTagId(String tagId) {
        mTagId = tagId;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void seticonUrl(String iconUrl) {
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
}
