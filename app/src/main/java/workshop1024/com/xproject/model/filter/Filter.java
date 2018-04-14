package workshop1024.com.xproject.model.filter;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * 阅读者创建的新闻过滤器
 */
@Entity(tableName = "filters")
public class Filter {
    //过滤器id
    @PrimaryKey
    @NonNull
    private String mFilterId;
    //过滤器名称
    @ColumnInfo(name = "filterName")
    private String mFilterName;

    @NonNull
    public String getFilterId() {
        return mFilterId;
    }

    public void setFilterId(String filterId) {
        mFilterId = filterId;
    }

    public String getFilterName() {
        return mFilterName;
    }

    public void setFilterName(String filterName) {
        mFilterName = filterName;
    }
}
