package workshop1024.com.xproject.feedback.model

import workshop1024.com.xproject.feedback.model.message.source.MessageDataSource
import workshop1024.com.xproject.feedback.model.message.source.MessageRepository

object Injection {

    fun provideMessageRepository(): MessageDataSource {
        return MessageRepository.instance
    }
}
