package com.sample.mvvm_data_binding_koin.interceptor

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.sample.mvvm_data_binding_koin.Constants.INDEX_LOGIN
import com.sample.mvvm_data_binding_koin.util.FileUtil.readStringFromAssetManager
import com.sample.mvvm_pratice.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import kotlin.jvm.Throws

class MockApiInterceptor(private val context: Context) : Interceptor {

    companion object {
        private val TAG = MockApiInterceptor::class.java.simpleName
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.toUri().path
        var response: Response?

        response = interceptRequestWhenDebug(chain, path)

        if (response == null) {
            response = chain.proceed(chain.request())
        }

        return response
    }

    private fun interceptRequestWhenDebug(chain: Interceptor.Chain, path: String): Response? {
        var response: Response? = null

        if (BuildConfig.DEBUG) {
            val request = chain.request()

            when {
                path.contains(INDEX_LOGIN) -> {
                    response = getMockJsonData(request, "index_login.mock")
                }
            }
        }
        return response
    }

    private fun getMockJsonData(request: Request, fileName: String): Response? {
        val assetManager = context.assets
        val path = "mock_api/$fileName"
        val data = readStringFromAssetManager(assetManager, path)
        return getHttpSuccessResponse(request, data)
    }

    private fun getHttpSuccessResponse(request: Request, dataJson: String?): Response {
        return if (TextUtils.isEmpty(dataJson)) {
            Log.d("MockApiInterceptor", "getHttpSuccessResponse: dataJson is empty!")

            Response.Builder()
                .code(500)
                .protocol(Protocol.HTTP_1_0)
                .request(request)
                .build()
        } else {

            Response.Builder()
                .code(200)
                .message(dataJson!!)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("Content-Type", "application/json")
                .body(dataJson.toResponseBody("application/json".toMediaType())).build()
        }
    }
}