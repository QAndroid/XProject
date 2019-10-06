package workshop1024.com.xproject.news.model.newsdetail.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import workshop1024.com.xproject.base.room.converts.XConverters
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail

@Database(entities = [NewsDetail::class], version = 1, exportSchema = false)
@TypeConverters(XConverters::class)
abstract class NewsDetailDatabase : RoomDatabase() {
    abstract fun newsDetailDao(): NewsDetailDao

    companion object {
        private lateinit var INSTANCE: NewsDetailDatabase

        private val sLock = Any()

        fun getInstance(context: Context): NewsDetailDatabase {
            synchronized(sLock) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NewsDetailDatabase::class.java,
                            "newsdetails.db").build()
                }
                return INSTANCE
            }
        }
    }
}