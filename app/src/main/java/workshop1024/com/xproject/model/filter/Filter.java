package workshop1024.com.xproject.model.filter;

public class Filter {
    //过滤器id
    private String mFilterId;
    //过滤器名称
    private String mFilterName;

    public Filter(String filterId, String filterName) {
        mFilterId = filterId;
        mFilterName = filterName;
    }

    public String getFilterId() {
        return mFilterId;
    }

    public void setFilterId(String filterId) {
        mFilterId = filterId;
    }

    public String getFilterName() {
        return mFilterName;
    }

    public void setFilterName(String filterName) {
        mFilterName = filterName;
    }
}
