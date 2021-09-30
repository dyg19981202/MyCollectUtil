package cn.dfordog.baseretrofit.repository

import cn.dfordog.baseretrofit.base.BaseRepository
import cn.dfordog.baseretrofit.pojo.Weather
import cn.dfordog.baseretrofit.service.Api

object ApiRepository: BaseRepository(){

    private val api by lazy {
        api(Api::class.java)
    }

    suspend fun weather(city: String,weather: (Weather) -> Unit){
        weather(api.weather(city))
    }

}