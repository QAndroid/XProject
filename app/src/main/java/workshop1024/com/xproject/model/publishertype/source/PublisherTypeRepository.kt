package workshop1024.com.xproject.model.publishertype.source

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import workshop1024.com.xproject.model.publishertype.PublisherType
import workshop1024.com.xproject.net.PublisherTypeService
import workshop1024.com.xproject.net.XRetrofit

class PublisherTypeRepository : PublisherTypeDataSource {
    override fun getPublisherContentTypes(loadPublisherTypeCallback: PublisherTypeDataSource.LoadPublisherTypeCallback?) {
        //TODO 如何处理网络错误等逻辑
        val retrofit = XRetrofit.retrofit
        val publisherTypeService = retrofit?.create(PublisherTypeService::class.java)
        val publisherTypesCall = publisherTypeService?.getPublisherContentTypes()
        publisherTypesCall?.enqueue(object : Callback<List<PublisherType>> {
            override fun onFailure(call: Call<List<PublisherType>>, t: Throwable) {
                loadPublisherTypeCallback?.onDataNotAvailable()
            }

            override fun onResponse(call: Call<List<PublisherType>>, response: Response<List<PublisherType>>) {
                val publisherTypeList = response.body()
                loadPublisherTypeCallback?.onPublisherTypesLoaded(publisherTypeList, "content")
            }
        })
    }

    override fun getPublisherLanguageTypes(loadPublisherTypeCallback: PublisherTypeDataSource.LoadPublisherTypeCallback?) {
        val retrofit = XRetrofit.retrofit
        val publisherTypeService = retrofit?.create(PublisherTypeService::class.java)
        val languageTypesCall = publisherTypeService?.getPublisherLanguageTypes()
        languageTypesCall?.enqueue(object : Callback<List<PublisherType>> {
            override fun onFailure(call: Call<List<PublisherType>>, t: Throwable) {
                loadPublisherTypeCallback?.onDataNotAvailable()
            }

            override fun onResponse(call: Call<List<PublisherType>>, response: Response<List<PublisherType>>) {
                val languageTypeList = response.body()
                loadPublisherTypeCallback?.onPublisherTypesLoaded(languageTypeList, "language")
            }
        })
    }

    companion object {
        private var INSTANCE: PublisherTypeRepository? = null

        val instance: PublisherTypeRepository?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = PublisherTypeRepository()
                }
                return INSTANCE
            }

    }
}