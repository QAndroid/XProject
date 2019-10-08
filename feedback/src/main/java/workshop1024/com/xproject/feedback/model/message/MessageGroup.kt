package workshop1024.com.xproject.feedback.model.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messagegroups")
data class MessageGroup(
        @PrimaryKey @ColumnInfo(name = "groupId") var mGroupId: String,
        @ColumnInfo(name = "publishData") var mPublishData: String,
        @ColumnInfo(name = "messageList") var mMessageList: List<Message>)
