package workshop1024.com.xproject.model.message.source;

import android.os.Handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.message.Message;
import workshop1024.com.xproject.model.message.MessageGroup;

public class MessageMockRepository implements MessageDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, MessageGroup> MESSAGEGROUP_SERVICE_DATA;

    private static MessageMockRepository INSTANCE;

    static {
        MESSAGEGROUP_SERVICE_DATA = new LinkedHashMap<>(2);

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("001", "0101-mock"));
        messageList.add(new Message("002", "0102"));
        messageList.add(new Message("003", "0103"));
        messageList.add(new Message("004", "0104"));
        messageList.add(new Message("005", "0105-mock"));
        MessageGroup messageGroup = new MessageGroup("g001", "2018-01-01", messageList);
        MESSAGEGROUP_SERVICE_DATA.put(messageGroup.getGroupId(), messageGroup);
        List<Message> messageList1 = new ArrayList<>();
        messageList1.add(new Message("001", "0201"));
        messageList1.add(new Message("002", "0202"));
        messageList1.add(new Message("003", "0203"));
        messageList1.add(new Message("004", "0204"));
        messageList1.add(new Message("005", "0205"));
        MessageGroup messageGroup1 = new MessageGroup("g002", "2018-02-02", messageList1);
        MESSAGEGROUP_SERVICE_DATA.put(messageGroup1.getGroupId(), messageGroup1);
        List<Message> messageList2 = new ArrayList<>();
        messageList2.add(new Message("001", "0301"));
        messageList2.add(new Message("002", "0302"));
        messageList2.add(new Message("003", "0303"));
        messageList2.add(new Message("004", "0304"));
        messageList2.add(new Message("005", "0305"));
        MessageGroup messageGroup2 = new MessageGroup("g003", "2018-03-03", messageList2);
        MESSAGEGROUP_SERVICE_DATA.put(messageGroup2.getGroupId(), messageGroup2);
        List<Message> messageList3 = new ArrayList<>();
        messageList3.add(new Message("001", "0401"));
        messageList3.add(new Message("002", "0402"));
        messageList3.add(new Message("003", "0403"));
        messageList3.add(new Message("004", "0404"));
        messageList3.add(new Message("005", "0405"));
        MessageGroup messageGroup3 = new MessageGroup("g004", "2018-04-04", messageList3);
        MESSAGEGROUP_SERVICE_DATA.put(messageGroup3.getGroupId(), messageGroup3);
        List<Message> messageList4 = new ArrayList<>();
        messageList4.add(new Message("001", "0501-mock"));
        messageList4.add(new Message("002", "0502"));
        messageList4.add(new Message("003", "0503-mock"));
        messageList4.add(new Message("004", "0504"));
        messageList4.add(new Message("005", "0505-mock"));
        MessageGroup messageGroup4 = new MessageGroup("g005", "2018-05-05", messageList4);
        MESSAGEGROUP_SERVICE_DATA.put(messageGroup4.getGroupId(), messageGroup4);
    }

    private MessageMockRepository() {

    }

    public static MessageDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageMockRepository();
        }

        return INSTANCE;
    }

    @Override
    public void getMessages(final LoadMessagesCallback loadMessagesCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<MessageGroup> messageGroupList = new ArrayList<>(MESSAGEGROUP_SERVICE_DATA.values());
                loadMessagesCallback.onMessagesLoaded(messageGroupList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void submitMessage(final Message message) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Message> messageList = new ArrayList<>();
                messageList.add(message);
                MessageGroup messageGroup = new MessageGroup("g999", "2018-06-06", messageList);
                MESSAGEGROUP_SERVICE_DATA.put(messageGroup.getGroupId(), messageGroup);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
