package workshop1024.com.xproject.main.net

import retrofit2.Call
import retrofit2.http.GET
import workshop1024.com.xproject.main.model.publishertype.PublisherType

interface PublisherTypeService {
    @GET("/contenttypes")
    fun getPublisherContentTypes(): Call<List<workshop1024.com.xproject.main.model.publishertype.PublisherType>>

    @GET("/languagetypes")
    fun getPublisherLanguageTypes(): Call<List<workshop1024.com.xproject.main.model.publishertype.PublisherType>>
}