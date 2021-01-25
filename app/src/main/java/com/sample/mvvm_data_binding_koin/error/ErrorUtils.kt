package com.sample.mvvm_data_binding_koin.error

import androidx.annotation.Nullable
import com.sample.mvvm_data_binding_koin.TestApi
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException


object ErrorUtils {

    @Nullable
    fun <T> parseError(response: Response<T>): T? {
        val converter: Converter<ResponseBody, APIError> = TestApi.retrofit
            .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))

        var error: APIError? = null

        response.errorBody()?.let {
            try {
                error = converter.convert(it)
            } catch (e: IOException) {
                throw e
            }
        }

        error?.let {
            if (it.success != null && it.code != null && it.msg != null) {
                val url = response.raw().request.url.toString()
                when {
//                    (url.contains(USER_INFO)) -> {
//                        @Suppress("UNCHECKED_CAST")
//                        return UserInfoResult(it.code, it.msg, it.success, null) as T
//                    }
                }
            }
        }

        return null
    }
}