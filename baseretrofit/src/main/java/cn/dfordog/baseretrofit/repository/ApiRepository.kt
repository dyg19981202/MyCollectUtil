package cn.dfordog.baseretrofit.repository

import cn.dfordog.baseretrofit.base.BaseRepository
import cn.dfordog.baseretrofit.service.Api

object ApiRepository: BaseRepository<Api>(Api::class.java){

    private val api by lazy {
        api()
    }

    suspend fun hello(str:(String) -> Unit){
        api.hello()
    }

}