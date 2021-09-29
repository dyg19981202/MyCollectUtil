package cn.dfordog.baseretrofit.base

import cn.dfordog.baseretrofit.utils.RetrofitUtil

open class BaseRepository<T>(api: Class<T>){

    private val apiServer = api

    fun api() = RetrofitUtil.getInstance().create(apiServer)

}