package com.sample.mvvm_data_binding_koin.base

import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import com.sample.mvvm_data_binding_koin.util.NetworkUtil
import retrofit2.Response
import java.net.SocketTimeoutException


abstract class BaseViewModel : ViewModel() {
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
//                    true -> return@async response.body()
//                    false -> return@async doResponseError(response)
                    true -> return@async
                    false -> return@async
                }

            } catch (e: Exception) {
//                return@async doOnException(context, e)
                return@async
            }
        }

//        return apiResult.await()
        return null
    }

    private fun <T : BaseResult> doNoConnect(context: Context): T? {
        _networkUnavailableMsg.postValue("連線異常")
        return null
    }

    private fun <T : BaseResult> doResponseError(response: Response<T>): T? {
//        val errorResult = ErrorUtils.parseError(response)
//        errorResult?.let {
//            if (it !is LogoutResult
//                && it.code == TokenError.EXPIRED.code
//                && it.code == TokenError.FAILURE.code
//                && it.code == TokenError.REPEAT_LOGIN.code
//            ) {
//                _errorResultToken.postValue(it)
//            }
//        }
//        return errorResult
        return null
    }

    private fun <T : BaseResult> doOnException(context: Context, exception: Exception): T? {
        when (exception) {
            is SocketTimeoutException -> doOnTimeOutException(context)
            else -> doOnUnknownException(exception)
        }
        return null
    }

    private fun doOnTimeOutException(context: Context) {
        _networkExceptionTimeout.postValue("連線過時")
    }

    private fun doOnUnknownException(exception: Exception) {
        _networkExceptionUnknown.postValue(exception)
    }
}