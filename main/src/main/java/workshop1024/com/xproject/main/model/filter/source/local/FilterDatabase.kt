package workshop1024.com.xproject.main.model.filter.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import workshop1024.com.xproject.main.model.filter.Filter

@Database(entities = [Filter::class], version = 1, exportSchema = false)
abstract class FilterDatabase : RoomDatabase() {

    abstract fun filterDao(): FilterDao

    companion object {
        private lateinit var INSTANCE: FilterDatabase

        private val sLock = Any()

        fun getInstance(context: Context): FilterDatabase {
            synchronized(sLock) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FilterDatabase::class.java,
                            "filters.db").build()
                }
                return INSTANCE
            }
        }
    }
}