package cn.dfordog.baseretrofit.service

import retrofit2.http.POST

interface Api {

    @POST("")
    suspend fun hello(): String

}