package cn.dfordog.baseretrofit.base

import cn.dfordog.baseretrofit.utils.RetrofitUtil

open class BaseRepository{

    protected fun <T> api(api: Class<T>):T = RetrofitUtil.getInstance().create(api)

}