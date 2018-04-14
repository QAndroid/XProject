package workshop1024.com.xproject.model.publisher;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * 发布者数据类
 */
@Entity(tableName = "publishers")
public class Publisher {
    //发布者id
    @PrimaryKey
    @NonNull
    private String mPublisherId;
    //发布者类型
    @ColumnInfo(name = "type")
    private String mType;
    //发布者语言类型
    @ColumnInfo(name = "language")
    private String mLanguage;
    //发布者图标URL
    @ColumnInfo(name = "iconUrl")
    private String mIconUrl;
    //发布者名称
    @ColumnInfo(name = "name")
    private String mName;
    //发布者订阅数量
    @ColumnInfo(name = "subscribeNum")
    private String mSubscribeNum;
    //发布者是否被订阅
    @ColumnInfo(name = "isSubscribed")
    private boolean mIsSubscribed;

    public Publisher(String publisherId, String type, String language, String iconUrl, String name,
                     String subscribeNum, boolean isSubscribed) {
        mPublisherId = publisherId;
        mType = type;
        mLanguage = language;
        mIconUrl = iconUrl;
        mName = name;
        mSubscribeNum = subscribeNum;
        mIsSubscribed = isSubscribed;
    }

    public String getPublisherId() {
        return mPublisherId;
    }

    public void setPublisherId(String publisherId) {
        mPublisherId = publisherId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
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

    public boolean isIsSubscribed() {
        return mIsSubscribed;
    }

    public void setIsSubscribed(boolean isSubscribed) {
        mIsSubscribed = isSubscribed;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "mPublisherId='" + mPublisherId + '\'' +
                ", mType='" + mType + '\'' +
                ", mLanguage='" + mLanguage + '\'' +
                ", mIconUrl='" + mIconUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mSubscribeNum='" + mSubscribeNum + '\'' +
                ", mIsSubscribed=" + mIsSubscribed +
                '}';
    }
}
