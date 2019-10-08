package workshop1024.com.xproject.feedback.model.message.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import workshop1024.com.xproject.feedback.model.message.MessageGroup

@Dao
interface MessageDao {
    @Query("SELECT * FROM messagegroups")
    fun getMessages(): List<MessageGroup>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessageGroup(messageGroup: MessageGroup)

    @Query("DELETE FROM messagegroups")
    fun deleteAllMessageGroup()
}