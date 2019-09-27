package workshop1024.com.xproject.main.model.publisher

import androidx.databinding.ObservableBoolean
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 发布者数据类
 */
@Entity(tableName = "publishers")
data class Publisher( //发布者id
        @PrimaryKey @ColumnInfo(name = "publisherId") var mPublisherId: String, //发布者
        @ColumnInfo(name = "mType") var mType: String,//发布者语言类型
        @ColumnInfo(name = "language") var mLanguage: String,//发布者图标URL
        @ColumnInfo(name = "iconUrl") var mIconUrl: String,//发布者名称
        @ColumnInfo(name = "mName") var mName: String,//发布者订阅数量
        @ColumnInfo(name = "subscribeNum") var mSubscribeNum: String,//发布者是否被订阅
        @ColumnInfo(name = "isSubscribed") var mIsSubscribed: ObservableBoolean //使用观察者数据对象自动刷新，参考：https://developer.android.com/topic/libraries/data-binding/observability
)