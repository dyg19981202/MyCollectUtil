package cn.dfordog.baseretrofit.service

import cn.dfordog.baseretrofit.config.KeyConfig.WEATHER_KEY
import cn.dfordog.baseretrofit.pojo.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("simpleWeather/query")
    suspend fun weather(
        @Query("city") city:String,
        @Query("key") key: String = WEATHER_KEY
    ): Weather


}