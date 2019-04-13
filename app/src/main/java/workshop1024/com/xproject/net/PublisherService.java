package workshop1024.com.xproject.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import workshop1024.com.xproject.model.publisher.Publisher;

public interface PublisherService {
    @GET("/publisherses/{typeId}")
    Call<List<Publisher>> getPublishersByType(@Path("typeId") String typeId);
}
