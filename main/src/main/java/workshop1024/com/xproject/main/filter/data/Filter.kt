package workshop1024.com.xproject.main.filter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filters")
data class Filter(//过滤器id
        @PrimaryKey @ColumnInfo(name = "filterId") var mFilterId: String, //过滤器名称
        @ColumnInfo(name = "filterName") var mFilterName: String)
