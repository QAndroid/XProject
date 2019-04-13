package workshop1024.com.xproject.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import workshop1024.com.xproject.model.publishertype.PublisherType;

public interface PublisherTypeService {
    @GET("/contenttypes")
    Call<List<PublisherType>> getPublisherContentTypes();

    @GET("/languagetypes")
    Call<List<PublisherType>> getPublisherLanguageTypes();
}
