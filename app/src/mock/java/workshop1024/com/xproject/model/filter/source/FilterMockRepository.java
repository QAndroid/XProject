package workshop1024.com.xproject.model.filter.source;

import android.os.Handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.filter.Filter;

public class FilterMockRepository implements FilterDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, Filter> FILTERS_SERVICE_DATA;

    private static FilterMockRepository INSTANCE;

    static {
        //TODO 如何本地提供Mock环境
        FILTERS_SERVICE_DATA = new LinkedHashMap<>(2);

        addFilter("f001", "china-mock");
        addFilter("f002", "android");
        addFilter("f003", "usa");
        addFilter("f004", "news");
        addFilter("f005", "rusia-mock");
        addFilter("f006", "fight");
        addFilter("f007", "boate");
        addFilter("f008", "car");
        addFilter("f009", "bycycle-mock");
    }

    private FilterMockRepository() {

    }

    private static void addFilter(String filterId, String filterName) {
        Filter filter = new Filter(filterId, filterName);
        FILTERS_SERVICE_DATA.put(filter.getFilterId(), filter);
    }

    public static FilterDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FilterMockRepository();
        }

        return INSTANCE;
    }

    @Override
    public void getFilters(final LoadFiltersCallback loadFiltersCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Filter> filterList = new ArrayList<>(FILTERS_SERVICE_DATA.values());
                loadFiltersCallback.onPublishersLoaded(filterList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void addFilterByName(final String filterName) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Filter filter = new Filter("f101", filterName);
                FILTERS_SERVICE_DATA.put(filter.getFilterId(), filter);
            }
        }, SERVICE_LATENCY_IN_MILLIS);

    }

    @Override
    public void deleteFilterById(final String filterId) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FILTERS_SERVICE_DATA.remove(filterId);
            }
        }, SERVICE_LATENCY_IN_MILLIS);

    }
}
