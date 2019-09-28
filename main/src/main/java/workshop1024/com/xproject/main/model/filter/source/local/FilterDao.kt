package workshop1024.com.xproject.main.model.filter.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import workshop1024.com.xproject.main.model.filter.Filter

@Dao
interface FilterDao {
    @Query("SELECT * FROM filters")
    fun getFilters(): List<Filter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFilter(filter: Filter)

    @Query("DELETE FROM filters")
    fun deleteAllFilters()

    @Query("DELETE FROM filters WHERE filterId = :filterId")
    fun deleteFilterById(filterId: String)
}