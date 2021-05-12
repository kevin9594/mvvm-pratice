package com.sample.mvvm_data_binding_koin

import com.sample.mvvm_data_binding_koin.Constants.INDEX_LOGIN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IndexService {

    @POST(INDEX_LOGIN)
    suspend fun login(): Response<LoginResult>
//    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResult>


}