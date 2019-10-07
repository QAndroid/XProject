package workshop1024.com.xproject.news.model.newsdetail.local

import android.content.Context
import androidx.room.*
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail

@Dao
interface NewsDetailDao {

    @Query("SELECT * FROM newsdetails WHERE newsId = :newsId")
    fun getNewsDetailByNewsId(newsId: String): NewsDetail

    @Query("DELETE FROM newsdetails WHERE newsId = :newsId")
    fun deleteNewsDetailsById(newsId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewsDetail(newsDetail: NewsDetail)

    @Query("UPDATE newsdetails SET isSaved = '1' WHERE newsId = :newsId")
    fun saveNewsById(newsId: String)
}