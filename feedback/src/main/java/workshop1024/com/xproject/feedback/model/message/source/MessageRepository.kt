package workshop1024.com.xproject.feedback.model.message.source

import android.util.Log

import workshop1024.com.xproject.feedback.model.message.Message
import workshop1024.com.xproject.feedback.model.message.MessageGroup

class MessageRepository private constructor(private val mMessageRemoteDataSource: MessageDataSource,
                                            private val mMessageLocalDataSource: MessageDataSource) : MessageDataSource {

    private lateinit var mCachedMessagesMaps: MutableMap<String, MessageGroup>
    private var mIsRequestCacheOrLocal: Boolean = true
    private var mIsRequestRemote: Boolean = true

    override fun getMessages(loadCallback: MessageDataSource.LoadCallback) {
        Log.i("XProject", "MessageRepository getMessages , mIsRequestRemote = $mIsRequestRemote mIsRequestCacheOrLocal = $mIsRequestCacheOrLocal")

        //优先取缓存，有缓存数据立即展示
        if (mIsRequestCacheOrLocal) {
            if (this::mCachedMessagesMaps.isInitialized) {
                getMessagesFromCache(loadCallback)
            } else {
                getMessagesFromLocal(loadCallback)
            }
        }

        if (mIsRequestRemote) {
            getMessagesFromRemote(loadCallback)
        }
    }


    private fun getMessagesFromRemote(loadCallback: MessageDataSource.LoadCallback) {
        Log.i("XProject", "MessageRepository getMessagesFromRemote")
        mMessageRemoteDataSource.getMessages(object : MessageDataSource.LoadRemoteMessagesCallback {
            override fun onRemoteMessagesLoaded(messageGroupList: List<MessageGroup>) {
                Log.i("XProject", "MessageRepository getMessagesFromRemote onRemoteMessagesLoaded, messageGroupList = ${messageGroupList.toString()}")
                refreshCached(messageGroupList)
                refreshLocal(messageGroupList)
                (loadCallback as MessageDataSource.LoadRemoteMessagesCallback).onRemoteMessagesLoaded(messageGroupList)

                mIsRequestRemote = false
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "MessageRepository getMessagesFromRemote onDataNotAvailable")
            }
        })
    }

    private fun refreshLocal(messageGroupList: List<MessageGroup>) {
        mMessageLocalDataSource.deleteAllMessageGroup()

        for (messageGroup in messageGroupList) {
            mMessageLocalDataSource.addMessageGroup(messageGroup)
        }

        mIsRequestCacheOrLocal = true
    }

    private fun getMessagesFromLocal(loadCallback: MessageDataSource.LoadCallback) {
        Log.i("XProject", "MessageRepository getMessagesFromLocal")
        mMessageLocalDataSource.getMessages(object : MessageDataSource.LoadCacheOrLocalMessagesCallback {
            override fun onCacheOrLocalMessagesLoaded(messageGroupList: List<MessageGroup>) {
                Log.i("XProject", "MessageRepository getMessagesFromLocal onCacheOrLocalMessagesLoaded, messageGroupList = ${messageGroupList.toString()}")
                refreshCached(messageGroupList)
                (loadCallback as MessageDataSource.LoadCacheOrLocalMessagesCallback).onCacheOrLocalMessagesLoaded(messageGroupList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "FilterRepository getFiltersFromLocal onDataNotAvailable")
            }
        })
    }

    private fun refreshCached(messageGroupList: List<MessageGroup>) {
        Log.i("XProject", "MessageRepository refreshCached, messageGroupList = ${messageGroupList.toString()}")
        if (!this::mCachedMessagesMaps.isInitialized) {
            mCachedMessagesMaps = LinkedHashMap()
        }

        mCachedMessagesMaps.clear()

        for (messageGroup in messageGroupList) {
            mCachedMessagesMaps.put(messageGroup.mGroupId, messageGroup)
        }

        mIsRequestCacheOrLocal = true
    }

    private fun getMessagesFromCache(loadCallback: MessageDataSource.LoadCallback) {
        Log.i("XProject", "MessageRepository getMessagesFromCache")
        val messageGroupList = ArrayList(mCachedMessagesMaps.values)
        if (!messageGroupList.isEmpty()) {
            (loadCallback as MessageDataSource.LoadCacheOrLocalMessagesCallback).onCacheOrLocalMessagesLoaded(messageGroupList)
        } else {
            getMessagesFromLocal(loadCallback)
        }
    }

    override fun deleteAllMessageGroup() {

    }

    override fun addMessageGroup(messageGroup: MessageGroup) {

    }

    override fun submitMessage(message: Message) {

    }

    override fun getIsRequestRemote(): Boolean {
        return mIsRequestRemote
    }

    override fun refresh() {
        mIsRequestCacheOrLocal = false
        mIsRequestRemote = true
    }

    companion object {
        private lateinit var INSTANCE: MessageRepository

        fun getInstance(messageRemoteDataSource: MessageDataSource, messageLocalDataSource: MessageDataSource): MessageRepository {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = MessageRepository(messageRemoteDataSource, messageLocalDataSource)
            }
            return INSTANCE
        }
    }
}
