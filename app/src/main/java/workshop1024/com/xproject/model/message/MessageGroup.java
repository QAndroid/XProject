package workshop1024.com.xproject.model.message;

import java.util.List;

public class MessageGroup {
    private String mPublishData;
    private List<Message> mMessageList;

    public MessageGroup(String publishData, List<Message> messageList) {
        mPublishData = publishData;
        mMessageList = messageList;
    }

    public String getPublishData() {
        return mPublishData;
    }

    public void setPublishData(String publishData) {
        mPublishData = publishData;
    }

    public List<Message> getMessageList() {
        return mMessageList;
    }

    public void setMessageList(List<Message> messageList) {
        mMessageList = messageList;
    }
}
