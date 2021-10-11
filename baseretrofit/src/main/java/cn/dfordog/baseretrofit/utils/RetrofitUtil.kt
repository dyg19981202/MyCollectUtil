package cn.dfordog.baseretrofit.utils

import cn.dfordog.baseretrofit.config.ConstConfig.DEFAULT_TIME_OUT
import cn.dfordog.baseretrofit.config.ConstConfig.TOKEN
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    private const val BASE_URL = "http://apis.juhe.cn"

    private val retrofit by lazy {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient().newBuilder()
            .addInterceptor(LoginFailInterceptor())
            .addInterceptor(Interceptor {
                val build = it.request().newBuilder().addHeader("token", TOKEN).build()
                return@Interceptor it.proceed(build)
            })
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
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
    fun getInstance(): Retrofit = retrofit
}
