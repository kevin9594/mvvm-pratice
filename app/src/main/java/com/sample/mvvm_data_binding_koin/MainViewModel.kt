package com.sample.mvvm_data_binding_koin


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.mvvm_data_binding_koin.base.BaseViewModel
import com.sample.mvvm_data_binding_koin.repository.UserInfoRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val androidContext: Context,
    private val userInfoRepository: UserInfoRepository,
) : BaseViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data


    fun getData(gameType: String) {
        viewModelScope.launch {
//            val result = doNetwork(androidContext) {
//                TestApi.indexService.login(null)
//            }
//            _data.postValue(result)
        }
    }


}