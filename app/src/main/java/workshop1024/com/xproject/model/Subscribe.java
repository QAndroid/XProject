package workshop1024.com.xproject.model;

/**
 * 订阅者数据类
 */
public class Subscribe {
    //订阅者图标url
    private String imageUrl;
    //订阅者名称
    private String name;
    //订阅者文章数量
    private String amount;

    public Subscribe(String imageUrl, String name, String amount) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
