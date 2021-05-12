package com.sample.mvvm_data_binding_koin.interceptor


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws


const val KEY_BASE_URL = "baseUrl"
class MoreBaseUrlInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val oldBaseUrl = request.url

        //拦截retrofit注解的header配置信息
        val list = request.headers(KEY_BASE_URL)

        if (list.isNotEmpty()) {
            //移除掉header 因为服务器不需要这个header,这个header只是在拦截器里用到
            builder.removeHeader(KEY_BASE_URL)
        }
        return  chain.proceed(request)
    }


}
