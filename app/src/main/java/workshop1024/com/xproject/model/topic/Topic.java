package workshop1024.com.xproject.model.topic;

/**
 * 主题数据类
 */
public class Topic {
    //主题图标url
    private String imageUrl;
    //主题名称
    private String topic;
    //主题文章数量
    private String amount;

    public Topic(String imageUrl, String topic, String amount) {
        this.imageUrl = imageUrl;
        this.topic = topic;
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
