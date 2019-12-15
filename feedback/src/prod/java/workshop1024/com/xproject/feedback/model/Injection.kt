package workshop1024.com.xproject.feedback.model

import android.content.Context
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.feedback.model.message.source.MessageDataSource
import workshop1024.com.xproject.feedback.model.message.source.MessageRepository
import workshop1024.com.xproject.feedback.model.message.source.local.MessageDatabase
import workshop1024.com.xproject.feedback.model.message.source.local.MessageLocalDataSource
import workshop1024.com.xproject.feedback.model.message.source.remote.MessageRemoteDataSource

object Injection {
    fun provideMessageRepository(context: Context): MessageDataSource {
        return MessageRepository.getInstance(MessageRemoteDataSource.instance,
                MessageLocalDataSource.getInstance(MessageDatabase.getInstance(context).messageDao(), ExecutorUtils()))
    }
}
