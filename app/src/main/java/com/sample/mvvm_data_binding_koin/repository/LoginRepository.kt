package com.sample.mvvm_data_binding_koin.repository


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.mvvm_data_binding_koin.LoginRequest
import com.sample.mvvm_data_binding_koin.LoginResult
import com.sample.mvvm_data_binding_koin.TestApi
import com.sample.mvvm_data_binding_koin.LoginData
import retrofit2.Response


const val NAME_LOGIN = "login"
const val KEY_TOKEN = "token"
const val KEY_ACCOUNT = "account"
const val KEY_PWD = "pwd"
const val KEY_PLATFORM_ID = "platformId"
const val KEY_REMEMBER_PWD = "remember_pwd"
const val KEY_ODDS_TYPE = "oddsType"
const val KEY_USER_ID = "user_id"


class LoginRepository(private val androidContext: Context) {


    private val sharedPref: SharedPreferences by lazy {
        androidContext.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE)
    }


    val isLogin: LiveData<Boolean>
        get() = _isLogin
    private val _isLogin = MutableLiveData<Boolean>()


    var platformId
        get() = sharedPref.getLong(KEY_PLATFORM_ID, -1)
        set(value) {
            with(sharedPref.edit()) {
                putLong(KEY_PLATFORM_ID, value)
                apply()
            }
        }


    var token
        get() = sharedPref.getString(KEY_TOKEN, "")
        set(value) {
            with(sharedPref.edit()) {
                putString(KEY_TOKEN, value)
                apply()
            }
        }


    var account
        get() = sharedPref.getString(KEY_ACCOUNT, "")
        set(value) {
            with(sharedPref.edit()) {
                putString(KEY_ACCOUNT, value)
                apply()
            }
        }


    var isRememberPWD
        get() = sharedPref.getBoolean(KEY_REMEMBER_PWD, false)
        set(value) {
            with(sharedPref.edit()) {
                putBoolean(KEY_REMEMBER_PWD, value)
                commit()
            }
        }


    var isCheckToken = false


    suspend fun login(loginRequest: LoginRequest): Response<LoginResult> {
        val loginResponse = TestApi.indexService.login()
        if (loginResponse.isSuccessful) {
            loginResponse.body()?.let {
                isCheckToken = true
                updateLoginData(it.loginData)
            }
        }

        return loginResponse
    }


    private fun getDeviceName(): String {
        val manufacturer: String = Build.MANUFACTURER
        val model: String = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }


    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }


    private fun updateLoginData(loginData: LoginData?) {

        _isLogin.postValue(loginData != null)

        with(sharedPref.edit()) {
            /*putBoolean(KEY_IS_LOGIN, loginData != null)*/
            putString(KEY_TOKEN, loginData?.token)
            putLong(KEY_USER_ID, loginData?.userId ?: -1)
            putLong(KEY_PLATFORM_ID, loginData?.platformId ?: -1)
            apply()
        }
    }


    suspend fun clear() {
        with(sharedPref.edit()) {
            remove(KEY_TOKEN)
            remove(KEY_ODDS_TYPE)
            apply()
        }
    }


}