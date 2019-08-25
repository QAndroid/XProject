package workshop1024.com.xproject.feedback.model.message.source

import workshop1024.com.xproject.feedback.model.message.Message
import workshop1024.com.xproject.feedback.model.message.MessageGroup

interface MessageDataSource {
    fun getMessages(loadMessagesCallback: LoadMessagesCallback)

    fun submitMessage(message: Message)

    interface LoadMessagesCallback {
        fun onMessagesLoaded(messageGroupList: List<MessageGroup>)

        fun onDataNotAvailable()
    }
}
