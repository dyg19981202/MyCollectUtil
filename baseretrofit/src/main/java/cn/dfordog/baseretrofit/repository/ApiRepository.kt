package cn.dfordog.baseretrofit.repository

import cn.dfordog.baseretrofit.base.BaseRepository
import cn.dfordog.baseretrofit.pojo.BasePojo
import cn.dfordog.baseretrofit.pojo.Result
import cn.dfordog.baseretrofit.pojo.Weather
import cn.dfordog.baseretrofit.service.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

object ApiRepository: BaseRepository(){

    private val api by lazy {
        api(Api::class.java)
    }

    suspend fun weather(city: String,weather: (Weather) -> Unit){
        weather(api.weather(city,""))
    }

    suspend fun get(city: String): BasePojo<Weather> =
        withContext(Dispatchers.IO) {
            try {
                return@withContext BasePojo(data = api.weather(city))
            } catch (e: Exception) {
                return@withContext BasePojo(
                    1,
                    e.message.toString(),
                    null
                )
            }
        }

}