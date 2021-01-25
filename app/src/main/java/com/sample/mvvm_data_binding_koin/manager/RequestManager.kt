package com.sample.mvvm_data_binding_koin.manager

import android.annotation.SuppressLint
import android.content.Context
import com.sample.mvvm_data_binding_koin.Constants.CONNECT_TIMEOUT
import com.sample.mvvm_data_binding_koin.Constants.READ_TIMEOUT
import com.sample.mvvm_data_binding_koin.Constants.WRITE_TIMEOUT
import com.sample.mvvm_data_binding_koin.NullValueAdapter
import com.sample.mvvm_data_binding_koin.interceptor.LogInterceptor
import com.sample.mvvm_data_binding_koin.interceptor.MockApiInterceptor
import com.sample.mvvm_pratice.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.cxct.sportlottery.network.interceptor.MoreBaseUrlInterceptor
import org.cxct.sportlottery.network.interceptor.RequestInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@SuppressLint("CheckResult")
class RequestManager private constructor(context: Context) {

    var retrofit: Retrofit

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(NullValueAdapter())
        .build()

    companion object {
        private lateinit var staticContext: Context
        val instance: RequestManager by lazy {
            RequestManager(staticContext)
        }

        fun init(context: Context) {
            staticContext = context
        }
    }

    init {



        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(MoreBaseUrlInterceptor())
            .addInterceptor(RequestInterceptor(context))

        okHttpClientBuilder.addInterceptor(
            LogInterceptor()
                .setLevel(LogInterceptor.Level.BODY))

        // mock data, 必須擺在最後
        if (BuildConfig.MOCK) {
            okHttpClientBuilder.addInterceptor(MockApiInterceptor(context))
        }



        retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}
