package workshop1024.com.xproject.main.model.publisher.local

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.room.*
import workshop1024.com.xproject.main.model.publisher.Publisher
import workshop1024.com.xproject.main.model.publisher.PublisherType

//java.lang.IllegalStateException: Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.
//TODO Room数据库升级如何处理？？
@Database(entities = [Publisher::class, PublisherType::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PublisherDatabase : RoomDatabase() {
    abstract fun publisherDao(): PublisherDao

    companion object {
        private lateinit var INSTANCE: PublisherDatabase

        private val sLock = Any()

        fun getInstance(context: Context): PublisherDatabase {
            //FIXME 公共访问组件注意多线程并发问题
            synchronized(sLock) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, PublisherDatabase::class.java,
                            "publishers.db").build()
                }
                return INSTANCE
            }
        }
    }
}