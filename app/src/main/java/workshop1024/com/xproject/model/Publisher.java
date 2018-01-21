package workshop1024.com.xproject.model;

/**
 * 发布者数据类
 */
public class Publisher {
    //发布者图标url
    private String imageUrl;
    //发布者名称
    private String name;
    //发布者文章数量
    private String amount;

    public Publisher(String imageUrl, String name, String amount) {
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
