package workshop1024.com.xproject.news.model.news.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import workshop1024.com.xproject.news.model.news.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM newses WHERE searchType = :searchType AND searchKey = :searchKey")
    fun getNewsesByTypeAndKey(searchType: String, searchKey: String): List<News>

    @Query("DELETE FROM newses WHERE searchType = :searchType AND searchKey = :searchKey")
    fun deleteAllNewsesByTypeAndKey(searchType: String, searchKey: String)

    @Query("UPDATE newses SET isReaded = 1 WHERE newsId = :newsId")
    fun markNewsReadedByNewsId(newsId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews(news: News)
}