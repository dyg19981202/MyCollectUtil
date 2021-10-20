package cn.dfordog.baseretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.dfordog.baseretrofit.base.BaseViewModel
import cn.dfordog.baseretrofit.pojo.BasePojo
import cn.dfordog.baseretrofit.pojo.Weather
import cn.dfordog.baseretrofit.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private var __weather = MutableLiveData<BasePojo<Weather>>()
    private var _weather = MutableLiveData<Weather>()
    var city = "临沂"

    /**
     * 获取天气
     */
    fun getWeather(): LiveData<BasePojo<Weather>>{
        viewModelScope.launch(Dispatchers.IO) {
            __weather.postValue(ApiRepository.get(city))
        }
        return __weather
    }

    fun weather(): LiveData<Weather>{
        viewModelScope.launch(Dispatchers.IO) {
            ApiRepository.weather(city){
                _weather.postValue(it)
            }
        }
        return _weather
    }
}