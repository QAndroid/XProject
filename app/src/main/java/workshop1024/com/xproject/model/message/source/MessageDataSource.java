package workshop1024.com.xproject.model.message.source;

import java.util.List;

import workshop1024.com.xproject.model.message.Message;
import workshop1024.com.xproject.model.message.MessageGroup;

public interface MessageDataSource {
    void getMessages(LoadMessagesCallback loadMessagesCallback);

    void submitMessage(Message message);

    interface LoadMessagesCallback {
        void onMessagesLoaded(List<MessageGroup> messageGroupList);

        void onDataNotAvailable();
    }
}
