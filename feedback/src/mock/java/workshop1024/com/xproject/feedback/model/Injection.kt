package workshop1024.com.xproject.feedback.model

import workshop1024.com.xproject.feedback.model.message.source.MessageDataSource
import workshop1024.com.xproject.feedback.model.message.source.MessageMockRepository

object Injection {
    fun provideMessageRepository(): MessageDataSource {
        return MessageMockRepository.instance
    }
}