package cn.dfordog.baseretrofit.pojo

import java.io.Serializable

data class BasePojo<T>(
    val code: Int = 1,
    val msg: String = "Success",
    var data: T?
) : Serializable