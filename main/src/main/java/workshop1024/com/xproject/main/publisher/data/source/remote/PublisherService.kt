package workshop1024.com.xproject.main.publisher.data.source.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import workshop1024.com.xproject.main.publisher.data.Publisher

interface PublisherService {
    @GET("/publisherses/{contentId}")
    fun getPublishersByContentType(@Path("contentId") contentId: String): Call<List<Publisher>>

    @GET("/publisherses/{languageId}")
    fun getPublishersByLanguageType(@Path("languageId") languageId: String): Call<List<Publisher>>
}