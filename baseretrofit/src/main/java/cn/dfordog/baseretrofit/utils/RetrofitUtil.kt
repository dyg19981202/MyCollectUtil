package cn.dfordog.baseretrofit.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitUtil {

    const val BASE_URL = ""

    private val retrofit by lazy {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient().newBuilder()
            .addInterceptor(LoginFailInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(LenientGsonConverterFactory.create(gson))
            .build()
    }

    /**
     * 返回创建的Retrofit
     */
    fun getInstance() = retrofit
}
