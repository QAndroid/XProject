package workshop1024.com.xproject.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import workshop1024.com.xproject.XConstant;

public class XRetrofit {
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(XConstant.SERVER_URL).addConverterFactory(GsonConverterFactory.
                    create()).build();
        }
        return mRetrofit;
    }
}
