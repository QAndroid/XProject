package workshop1024.com.xproject.net

import retrofit2.Call
import retrofit2.http.GET
import workshop1024.com.xproject.model.publishertype.PublisherType

interface PublisherTypeService {
    @GET("/contenttypes")
    fun getPublisherContentTypes(): Call<List<PublisherType>>

    @GET("/languagetypes")
    fun getPublisherLanguageTypes(): Call<List<PublisherType>>
}