package workshop1024.com.xproject.model.filter.source;

import java.util.List;

import workshop1024.com.xproject.model.filter.Filter;

public interface FilterDataSource {
    void getFilters(LoadFiltersCallback loadFiltersCallback);

    void addFilterByName(String filterName);

    void deleteFilterById(String filterId);

    interface LoadFiltersCallback {
        void onPublishersLoaded(List<Filter> filterList);

        void onDataNotAvailable();
    }
}
