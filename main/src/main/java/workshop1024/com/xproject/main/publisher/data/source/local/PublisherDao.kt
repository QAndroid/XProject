package workshop1024.com.xproject.main.publisher.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType

@Dao
interface PublisherDao {
    @Query("SELECT * FROM publishers")
    fun getPublishers(): List<Publisher>

    @Query("UPDATE publishers SET isSubscribed = 1 WHERE publisherId = :publisherId")
    fun subscribePublisherById(publisherId: String)

    @Query("UPDATE publishers SET isSubscribed = 0 WHERE publisherId = :publisherId")
    fun unSubscribePublisherById(publisherId: String)

    @Query("DELETE FROM publishers")
    fun deleteAllPublishers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPublisher(publisher: Publisher)

    @Query("SELECT * FROM publishertypes WHERE type = :type")
    fun getPublishersByType(type: String): List<PublisherType>

    @Query("DELETE FROM publishertypes")
    fun deleteAllPublisherTypes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePublisherType(publisherType: PublisherType)

    @Query("SELECT * FROM publishertypes")
    fun getPublisherTypes(): List<PublisherType>
}