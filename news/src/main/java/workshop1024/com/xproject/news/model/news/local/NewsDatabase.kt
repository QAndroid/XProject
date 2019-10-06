package workshop1024.com.xproject.news.model.news.local

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import workshop1024.com.xproject.base.room.converts.XConverters
import workshop1024.com.xproject.news.model.news.News


@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(XConverters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        private lateinit var INSTANCE: NewsDatabase

        private val sLock = Any()

        fun getInstance(context: Context): NewsDatabase {
            synchronized(sLock) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java,
                            "newses.db").build()
                }
                return INSTANCE
            }
        }
    }
}