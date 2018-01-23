package workshop1024.com.xproject.model;

/**
 * 发布者数据类
 */
public class Publisher {
    private String iconUrl;
    private String name;
    private String subscribeNum;

    public Publisher(String iconUrl, String name, String subscribeNum) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.subscribeNum = subscribeNum;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscribeNum() {
        return subscribeNum;
    }

    public void setSubscribeNum(String subscribeNum) {
        this.subscribeNum = subscribeNum;
    }
}
