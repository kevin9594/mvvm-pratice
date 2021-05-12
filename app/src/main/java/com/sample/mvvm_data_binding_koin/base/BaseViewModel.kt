package com.sample.mvvm_data_binding_koin.base


import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvm_data_binding_koin.error.ErrorUtils
import com.sample.mvvm_data_binding_koin.error.HttpError
import com.sample.mvvm_data_binding_koin.repository.LoginRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.sample.mvvm_data_binding_koin.util.NetworkUtil
import retrofit2.Response
import java.net.SocketTimeoutException


abstract class BaseViewModel(loginRepository: LoginRepository) : ViewModel() {


    val errorResultToken: LiveData<BaseResult>
        get() = _errorResultToken


    val networkUnavailableMsg: LiveData<String>
        get() = _networkUnavailableMsg


    val networkExceptionTimeout: LiveData<String>
        get() = _networkExceptionTimeout


    val networkExceptionUnknown: LiveData<Exception>
        get() = _networkExceptionUnknown


    private val _errorResultToken = MutableLiveData<BaseResult>()
    private val _networkUnavailableMsg = MutableLiveData<String>()
    private val _networkExceptionTimeout = MutableLiveData<String>()
    private val _networkExceptionUnknown = MutableLiveData<Exception>()


    @Nullable
    suspend fun <T : BaseResult> doNetwork(
        context: Context,
        apiFun: suspend () -> Response<T>
    ): T? {
        return when (NetworkUtil.isAvailable(context)) {
            true -> {
                doApiFun(context, apiFun)
            }
            false -> {
                doNoConnect(context)
            }
        }
    }


    private suspend fun <T : BaseResult> doApiFun(
        context: Context,
        apiFun: suspend () -> Response<T>
    ): T? {
        val apiResult = viewModelScope.async {
            try {
                val response = apiFun()
                when (response.isSuccessful) {
                    true -> return@async response.body()
                    false -> return@async doResponseError(response)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                return@async doOnException(context, e)
            }
        }

        return apiResult.await()
    }


    private fun <T : BaseResult> doNoConnect(context: Context): T? {
        _networkUnavailableMsg.postValue("檢查網路重新連線")
        return null
    }


    private fun <T : BaseResult> doResponseError(response: Response<T>): T? {
        val errorResult = ErrorUtils.parseError(response)
        if (response.code() == HttpError.UNAUTHORIZED.code) {
            errorResult?.let {
                _errorResultToken.postValue(it)
            }
        }
        return errorResult
    }


    fun doLogoutCleanUser() {
        viewModelScope.launch {
            //TODO clear data
        }
    }


    private fun <T : BaseResult> doOnException(context: Context, exception: Exception): T? {
        when (exception) {
            is SocketTimeoutException -> doOnTimeOutException(context)
            else -> doOnUnknownException(exception)
        }
        return null
    }


    private fun doOnTimeOutException(context: Context) {
        _networkExceptionTimeout.postValue("連線超時")
    }


    private fun doOnUnknownException(exception: Exception) {
        _networkExceptionUnknown.postValue(exception)
    }


}