package workshop1024.com.xproject.model.message;

public class Message {
    //消息id
    private String mMessageId;
    //消息内容
    private String mContent;

    public Message(String messageId, String content) {
        mMessageId = messageId;
        mContent = content;
    }

    public String getMessageId() {
        return mMessageId;
    }

    public void setMessageId(String messageId) {
        mMessageId = mMessageId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
