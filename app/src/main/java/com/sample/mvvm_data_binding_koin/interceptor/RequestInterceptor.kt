package com.sample.mvvm_data_binding_koin.interceptor

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import com.sample.mvvm_data_binding_koin.repository.KEY_TOKEN
import com.sample.mvvm_data_binding_koin.repository.NAME_LOGIN
import timber.log.Timber

import java.io.IOException
import kotlin.jvm.Throws

class RequestInterceptor(private val context: Context?) : Interceptor {
    private val sharedPref: SharedPreferences? by lazy {
        context?.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE)
    }

    companion object {
        val TAG = RequestInterceptor::class.java.simpleName
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (context == null) {
            throw NullPointerException("Please call RequestManager.getInstance().init(context) first")
        }
        val request = chain.request()
        val builder = request.newBuilder()
        val urlBuilder = request.url.newBuilder()

        // adds the pre-encoded query parameter to this URL's query string
        // urlBuilder.addEncodedQueryParameter("encoded", "qazwsx")

        // encodes the query parameter using UTF-8 and adds it to this URL's query string
        // urlBuilder.addQueryParameter("haha", "good")

        // header
        // ex : builder.addHeader("appKey", BuildConfig.APP_KEY)

//        builder.addHeader("x-lang", LanguageManager.getSelectLanguage(context).key)
        sharedPref?.getString(KEY_TOKEN, "")?.let {
            builder.addHeader("x-session-token", it)
        }

        val httpUrl = urlBuilder.build()
        val newRequest = builder.url(httpUrl).build()

        return try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            Timber.e("intercept Exception:$e")
            chain.proceed(request)
        }

    }

}
