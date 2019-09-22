package workshop1024.com.xproject.home.model.subinfo.source.local

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import workshop1024.com.xproject.home.model.subinfo.SubInfo

@Database(entities = [SubInfo::class], version = 1, exportSchema = false)
abstract class SubInfoDatabase : RoomDatabase() {

    abstract fun subInfoDao(): SubInfoDao

    companion object {
        private lateinit var INSTANCE: SubInfoDatabase

        private val sLock = Any()

        fun getInstance(context: Context): SubInfoDatabase {
            synchronized(sLock) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, SubInfoDatabase::class.java,
                            "subinfoes.db").build()
                }
                return INSTANCE
            }
        }
    }
}
