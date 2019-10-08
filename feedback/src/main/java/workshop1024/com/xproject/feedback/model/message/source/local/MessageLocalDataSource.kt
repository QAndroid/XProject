package workshop1024.com.xproject.feedback.model.message.source.local

import android.util.Log
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.feedback.model.message.Message
import workshop1024.com.xproject.feedback.model.message.MessageGroup
import workshop1024.com.xproject.feedback.model.message.source.MessageDataSource

class MessageLocalDataSource private constructor(private val mMessageDao: MessageDao, private val mExecutorUtils: ExecutorUtils) : MessageDataSource {

    override fun getMessages(loadCallback: MessageDataSource.LoadCallback) {
        Log.i("XProject", "MessageLocalDataSource getMessages")
        val getMessagesRunnable = Runnable {
            val messageGroupList = mMessageDao.getMessages()
            mExecutorUtils.mMainThreadExecutor.execute(Runnable {
                if (!messageGroupList.isEmpty()) {
                    (loadCallback as MessageDataSource.LoadCacheOrLocalMessagesCallback).onCacheOrLocalMessagesLoaded(messageGroupList)
                } else {
                    loadCallback.onDataNotAvailable()
                }
            })
        }

        mExecutorUtils.mDiskIOExecutor.execute(getMessagesRunnable)
    }

    override fun deleteAllMessageGroup() {
        Log.i("XProject", "MessageLocalDataSource deleteAllMessageGroup")
        val deleteAllMessageGroupRunnable = Runnable { mMessageDao.deleteAllMessageGroup() }
        mExecutorUtils.mDiskIOExecutor.execute(deleteAllMessageGroupRunnable)
    }

    override fun addMessageGroup(messageGroup: MessageGroup) {
        Log.i("XProject", "MessageLocalDataSource addMessageGroup, messageGroup = ${messageGroup.toString()}")
        val addMessageGroupRunnable = Runnable { mMessageDao.addMessageGroup(messageGroup) }
        mExecutorUtils.mDiskIOExecutor.execute(addMessageGroupRunnable)
    }

    override fun submitMessage(message: Message) {
        Log.i("XProject", "MessageLocalDataSource submitMessage, message = ${message.toString()}")
        val messageGroup = MessageGroup("g999", "2018-06-06", mutableListOf(message))
        addMessageGroup(messageGroup)
    }

    override fun getIsRequestRemote(): Boolean {
        return false
    }

    override fun refresh() {

    }

    companion object {
        private lateinit var INSTANCE: MessageLocalDataSource

        fun getInstance(messageDao: MessageDao, executorUtils: ExecutorUtils): MessageLocalDataSource {
            synchronized(MessageLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = MessageLocalDataSource(messageDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}