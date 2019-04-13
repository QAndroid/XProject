package workshop1024.com.xproject.model.publisher;


import android.databinding.ObservableBoolean;

/**
 * 发布者数据类
 */
public class Publisher {
    //发布者id
    private String mPublisherId;
    //发布者类型
    private String mTypeId;
    //发布者语言类型
    private String mLanguage;
    //发布者图标URL
    private String mIconUrl;
    //发布者名称
    private String mName;
    //发布者订阅数量
    private String mSubscribeNum;
    //发布者是否被订阅
    public final ObservableBoolean isSubscribed = new ObservableBoolean();

    public Publisher(String publisherId, String type, String language, String iconUrl, String name,
                     String subscribeNum, boolean isSubscribed) {
        mPublisherId = publisherId;
        mTypeId = type;
        mLanguage = language;
        mIconUrl = iconUrl;
        mName = name;
        mSubscribeNum = subscribeNum;
        this.isSubscribed.set(isSubscribed);
    }

    public String getPublisherId() {
        return mPublisherId;
    }

    public void setPublisherId(String publisherId) {
        mPublisherId = publisherId;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String typeId) {
        mTypeId = typeId;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
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

    public String getSubscribeNum() {
        return mSubscribeNum;
    }

    public void setSubscribeNum(String subscribeNum) {
        mSubscribeNum = subscribeNum;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "mPublisherId='" + mPublisherId + '\'' +
                ", mTypeId='" + mTypeId + '\'' +
                ", mLanguage='" + mLanguage + '\'' +
                ", mIconUrl='" + mIconUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mSubscribeNum='" + mSubscribeNum + '\'' +
                ", mIsSubscribed=" + isSubscribed +
                '}';
    }
}
