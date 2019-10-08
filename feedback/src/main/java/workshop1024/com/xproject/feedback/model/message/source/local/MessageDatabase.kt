package workshop1024.com.xproject.feedback.model.message.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import workshop1024.com.xproject.base.room.converts.XConverters
import workshop1024.com.xproject.feedback.model.message.MessageGroup

@Database(entities = [MessageGroup::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        private lateinit var INSTANCE: MessageDatabase

        private val sLock = Any()

        fun getInstance(context: Context): MessageDatabase {
            synchronized(sLock) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MessageDatabase::class.java,
                            "messagegroups.db").build()
                }
                return INSTANCE
            }
        }
    }
}