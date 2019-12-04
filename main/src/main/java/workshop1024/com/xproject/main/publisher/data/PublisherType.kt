package workshop1024.com.xproject.main.publisher.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 发布者内容类型
 */
@Entity(tableName = "publishertypes")
data class PublisherType(//单选项类型id
        @PrimaryKey @ColumnInfo(name = "typeId") var mTypeId: String, //单选类型
        @ColumnInfo(name = "type")var mType: String, // 单选项名称
        @ColumnInfo(name = "name")var mName: String)