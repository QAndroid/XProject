package workshop1024.com.xproject.model.publishertype.source;

import android.os.Handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.publishertype.PublisherType;

public class PublisherTypeMockRepository implements PublisherTypeDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, PublisherType> CONTENT_SERVICE_DATA;
    private static Map<String, PublisherType> LANGUAGE_SERVICE_DATA;

    private static PublisherTypeDataSource INSTANCE;

    static {
        //TODO 如何本地提供Mock环境
        CONTENT_SERVICE_DATA = new LinkedHashMap<>(2);
        addContentType("t001", "Tech");
        addContentType("t002", "News");
        addContentType("t003", "Business");
        addContentType("t004", "Health");
        addContentType("t005", "Gaming");
        addContentType("t006", "Design");
        addContentType("t007", "Fashion");
        addContentType("t008", "Cooking");
        addContentType("t009", "Comics");
        addContentType("t010", "DIY");
        addContentType("t011", "Sport");
        addContentType("t012", "Cinema");
        addContentType("t013", "Youtube");
        addContentType("t014", "Funny");
        addContentType("t015", "Esty");

        LANGUAGE_SERVICE_DATA = new LinkedHashMap<>(2);
        addLanguageType("l001", "English");
        addLanguageType("l002", "日语");
        addLanguageType("l003", "中文");
    }

    private PublisherTypeMockRepository() {

    }

    private static void addContentType(String typeId, String name) {
        PublisherType publisherType = new PublisherType(typeId, name);
        CONTENT_SERVICE_DATA.put(publisherType.getTypeId(), publisherType);
    }

    private static void addLanguageType(String typeId, String name) {
        PublisherType publisherType = new PublisherType(typeId, name);
        LANGUAGE_SERVICE_DATA.put(publisherType.getTypeId(), publisherType);
    }

    public static PublisherTypeDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherTypeMockRepository();
        }

        return INSTANCE;
    }

    @Override
    public void getPublisherContentTypes(final LoadPublisherTypeCallback loadPublisherTypeCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<PublisherType> publisherTypeList = new ArrayList<>(CONTENT_SERVICE_DATA.values());
                loadPublisherTypeCallback.onPublisherTypesLoaded(publisherTypeList, "content");
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getPublisherLanguageTypes(final LoadPublisherTypeCallback loadPublisherTypeCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<PublisherType> publisherTypeList = new ArrayList<>(LANGUAGE_SERVICE_DATA.values());
                loadPublisherTypeCallback.onPublisherTypesLoaded(publisherTypeList, "language");
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
