package workshop1024.com.xproject.model.tag;

/**
 * 新闻标记，用于对新闻进行分类，每条新闻会具有一系列的标记
 */
public class Tag {
    //新闻标记名称
    private String mName;
    //新闻标记Icon
    private String mIconUrl;
    //该类型标记未阅读新闻的数量
    private int mUnReadedCount;

    public Tag(String name, String iconUrl) {
        mName = name;
        mIconUrl = iconUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public int getUnReadedCount() {
        return mUnReadedCount;
    }

    public void setUnReadedCount(int unReadedCount) {
        mUnReadedCount = unReadedCount;
    }
}
