package workshop1024.com.xproject.feedback.model.message.source

import android.os.Handler
import workshop1024.com.xproject.feedback.model.message.Message
import workshop1024.com.xproject.feedback.model.message.MessageGroup
import workshop1024.com.xproject.feedback.model.message.source.MessageDataSource

import java.util.ArrayList
import java.util.LinkedHashMap

class MessageMockRepository private constructor() : MessageDataSource {

    override fun getMessages(loadMessagesCallback: MessageDataSource.LoadMessagesCallback) {
        val handler = Handler()
        handler.postDelayed({
            val messageGroupList = ArrayList(MESSAGEGROUP_SERVICE_DATA!!.values)
            loadMessagesCallback.onMessagesLoaded(messageGroupList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun submitMessage(message: Message) {
        val handler = Handler()
        handler.postDelayed({
            val messageList = ArrayList<Message>()
            messageList.add(message)
            val messageGroup = MessageGroup("g999", "2018-06-06", messageList)
            MESSAGEGROUP_SERVICE_DATA!![messageGroup.groupId!!] = messageGroup
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 1000

        private var MESSAGEGROUP_SERVICE_DATA: MutableMap<String, MessageGroup>? = null

        private var INSTANCE: MessageMockRepository? = null

        init {
            MESSAGEGROUP_SERVICE_DATA = LinkedHashMap(2)

            val messageList = ArrayList<Message>()
            messageList.add(Message("001", "0101-mock"))
            messageList.add(Message("002", "0102"))
            messageList.add(Message("003", "0103"))
            messageList.add(Message("004", "0104"))
            messageList.add(Message("005", "0105-mock"))
            val messageGroup = MessageGroup("g001", "2018-01-01", messageList)
            MESSAGEGROUP_SERVICE_DATA!![messageGroup.groupId!!] = messageGroup
            val messageList1 = ArrayList<Message>()
            messageList1.add(Message("001", "0201"))
            messageList1.add(Message("002", "0202"))
            messageList1.add(Message("003", "0203"))
            messageList1.add(Message("004", "0204"))
            messageList1.add(Message("005", "0205"))
            val messageGroup1 = MessageGroup("g002", "2018-02-02", messageList1)
            MESSAGEGROUP_SERVICE_DATA!![messageGroup1.groupId!!] = messageGroup1
            val messageList2 = ArrayList<Message>()
            messageList2.add(Message("001", "0301"))
            messageList2.add(Message("002", "0302"))
            messageList2.add(Message("003", "0303"))
            messageList2.add(Message("004", "0304"))
            messageList2.add(Message("005", "0305"))
            val messageGroup2 = MessageGroup("g003", "2018-03-03", messageList2)
            MESSAGEGROUP_SERVICE_DATA!![messageGroup2.groupId!!] = messageGroup2
            val messageList3 = ArrayList<Message>()
            messageList3.add(Message("001", "0401"))
            messageList3.add(Message("002", "0402"))
            messageList3.add(Message("003", "0403"))
            messageList3.add(Message("004", "0404"))
            messageList3.add(Message("005", "0405"))
            val messageGroup3 = MessageGroup("g004", "2018-04-04", messageList3)
            MESSAGEGROUP_SERVICE_DATA!![messageGroup3.groupId!!] = messageGroup3
            val messageList4 = ArrayList<Message>()
            messageList4.add(Message("001", "0501-mock"))
            messageList4.add(Message("002", "0502"))
            messageList4.add(Message("003", "0503-mock"))
            messageList4.add(Message("004", "0504"))
            messageList4.add(Message("005", "0505-mock"))
            val messageGroup4 = MessageGroup("g005", "2018-05-05", messageList4)
            MESSAGEGROUP_SERVICE_DATA!![messageGroup4.groupId!!] = messageGroup4
        }

        val instance: MessageDataSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MessageMockRepository()
                }

                return INSTANCE!!
            }
    }
}
