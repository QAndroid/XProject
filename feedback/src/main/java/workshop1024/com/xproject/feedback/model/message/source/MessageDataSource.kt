package workshop1024.com.xproject.feedback.model.message.source

import workshop1024.com.xproject.feedback.model.message.Message
import workshop1024.com.xproject.feedback.model.message.MessageGroup

interface MessageDataSource {
    fun getMessages(loadCallback: LoadCallback)

    fun deleteAllMessageGroup()

    fun addMessageGroup(messageGroup: MessageGroup)

    fun submitMessage(message: Message)

    fun getIsRequestRemote(): Boolean

    fun refresh()

    interface LoadMessagesCallback : LoadRemoteMessagesCallback, LoadCacheOrLocalMessagesCallback

    interface LoadCacheOrLocalMessagesCallback : LoadCallback {
        fun onCacheOrLocalMessagesLoaded(messageGroupList: List<MessageGroup>)
    }

    interface LoadRemoteMessagesCallback : LoadCallback {
        fun onRemoteMessagesLoaded(messageGroupList: List<MessageGroup>)
    }

    interface LoadCallback {
        fun onDataNotAvailable()
    }
}
