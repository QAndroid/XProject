package workshop1024.com.xproject.news.model.newsdetail.local

import android.content.Context
import androidx.room.*
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail

@Dao
interface NewsDetailDao {

    @Query("SELECT * FROM newsdetails WHERE newId = :newId")
    fun getNewsDetailByNewsId(newId: String): NewsDetail

    @Query("DELETE FROM newsdetails")
    fun deleteAllNewsDetails()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewsDetail(newsDetail: NewsDetail)
}