package workshop1024.com.xproject.base.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import workshop1024.com.xproject.base.XConstant

object XRetrofit {
    private var mRetrofit: Retrofit? = null

    val retrofit: Retrofit?
        get() {
            if (mRetrofit === null) {
                mRetrofit = Retrofit.Builder().baseUrl(XConstant.SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return mRetrofit
        }
}