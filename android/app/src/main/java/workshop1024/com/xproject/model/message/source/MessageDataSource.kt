package workshop1024.com.xproject.model.message.source

import workshop1024.com.xproject.model.message.Message
import workshop1024.com.xproject.model.message.MessageGroup

interface MessageDataSource {
    fun getMessages(loadMessagesCallback: LoadMessagesCallback)

    fun submitMessage(message: Message)

    interface LoadMessagesCallback {
        fun onMessagesLoaded(messageGroupList: List<MessageGroup>)

        fun onDataNotAvailable()
    }
}
