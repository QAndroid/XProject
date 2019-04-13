package workshop1024.com.xproject.model.publishertype.source;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import workshop1024.com.xproject.model.publishertype.PublisherType;
import workshop1024.com.xproject.net.PublisherTypeService;
import workshop1024.com.xproject.net.XRetrofit;

public class PublisherTypeRepository implements PublisherTypeDataSource {
    private static PublisherTypeRepository INSTANCE;

    private PublisherTypeRepository() {

    }

    public static PublisherTypeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherTypeRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getPublisherContentTypes(final LoadPublisherTypeCallback loadPublisherTypeCallback) {
        //TODO 如何处理网络错误等逻辑
        Retrofit retrofit = XRetrofit.getRetrofit();
        PublisherTypeService publisherTypeService = retrofit.create(PublisherTypeService.class);
        Call<List<PublisherType>> publisherTypesCall = publisherTypeService.getPublisherContentTypes();
        publisherTypesCall.enqueue(new Callback<List<PublisherType>>() {
            @Override
            public void onResponse(Call<List<PublisherType>> call, Response<List<PublisherType>> response) {
                List<PublisherType> publisherTypeList = response.body();
                loadPublisherTypeCallback.onPublisherTypesLoaded(publisherTypeList, "content");
            }

            @Override
            public void onFailure(Call<List<PublisherType>> call, Throwable t) {
                loadPublisherTypeCallback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPublisherLanguageTypes(final LoadPublisherTypeCallback loadPublisherTypeCallback) {
        Retrofit retrofit = XRetrofit.getRetrofit();
        PublisherTypeService languageTypeService = retrofit.create(PublisherTypeService.class);
        Call<List<PublisherType>> languageTypesCall = languageTypeService.getPublisherLanguageTypes();
        languageTypesCall.enqueue(new Callback<List<PublisherType>>() {
            @Override
            public void onResponse(Call<List<PublisherType>> call, Response<List<PublisherType>> response) {
                List<PublisherType> languageTypeList = response.body();
                loadPublisherTypeCallback.onPublisherTypesLoaded(languageTypeList, "language");
            }

            @Override
            public void onFailure(Call<List<PublisherType>> call, Throwable t) {
                loadPublisherTypeCallback.onDataNotAvailable();
            }
        });
    }
}
