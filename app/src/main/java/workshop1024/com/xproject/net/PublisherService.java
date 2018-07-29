package workshop1024.com.xproject.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import workshop1024.com.xproject.model.publisher.Publisher;

public interface PublisherService {
    @GET("/publisherses/{contentId}")
    Call<List<Publisher>> getPublishersByContentType(@Path("contentId") String contentId);

    @GET("/publisherses/{languageId}")
    Call<List<Publisher>> getPublishersByLanguageType(@Path("languageId") String languageId);
}
