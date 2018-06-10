package workshop1024.com.xproject.model.message;

import java.util.List;

public class MessageGroup {
    private String mGroupId;
    private String mPublishData;
    private List<Message> mMessageList;

    public MessageGroup(String groupId, String publishData, List<Message> messageList) {
        mGroupId = groupId;
        mPublishData = publishData;
        mMessageList = messageList;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
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
