package cn.dfordog.baseretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.dfordog.baseretrofit.base.BaseViewModel
import cn.dfordog.baseretrofit.repository.ApiRepository

class LoginViewModel : BaseViewModel() {

    private val _str = MutableLiveData<String>()

    suspend fun hello(): LiveData<String>{
        ApiRepository.hello{
            _str.postValue(it)
        }
        return _str
    }

}