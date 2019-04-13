package workshop1024.com.xproject.model.publishertype;

/**
 * 发布者内容类型
 */
public class PublisherType {
    //单选项类型id
    private String mTypeId;
    //单选项名称
    private String mName;

    public PublisherType(String typeId, String name) {
        mTypeId = typeId;
        mName = name;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String typeId) {
        mTypeId = typeId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
