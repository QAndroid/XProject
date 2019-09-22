package workshop1024.com.xproject.home.model.subinfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subinfoes")
data class SubInfo(//Info的id var
        @PrimaryKey @ColumnInfo(name = "infoId") var mInfoId: String, //Info图标URL
        @ColumnInfo(name = "infoType") var mInfoType: String,//Info类型
        @ColumnInfo(name = "iconUrl") var mIconUrl: String, //Info名称
        @ColumnInfo(name = "name") var mName: String, //Info未阅读新闻数量
        @ColumnInfo(name = "unreadCount") var mUnreadCount: String) {

    companion object {
        const val SUBSCRIBE_TYPE: String = "1"
        const val TAG_TYPE: String = "2"
        const val FILTER_TYPE: String = "3"
    }
}