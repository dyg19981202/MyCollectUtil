package cn.dfordog.baseretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.dfordog.baseretrofit.base.BaseViewModel
import cn.dfordog.baseretrofit.pojo.Weather
import cn.dfordog.baseretrofit.repository.ApiRepository

class LoginViewModel : BaseViewModel() {

    private val _weather = MutableLiveData<Weather>()
    var city = ""

    /**
     * 获取天气
     */
    suspend fun weather(): LiveData<Weather>{
        ApiRepository.weather(city){
            _weather.postValue(it)
        }
        return _weather
    }


}