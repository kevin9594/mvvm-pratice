package com.sample.mvvm_data_binding_koin

import com.sample.mvvm_data_binding_koin.base.BaseResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LoginResult(
    @Json(name = "code")
    override val code: Int,
    @Json(name = "msg")
    override val msg: String,
    @Json(name = "success")
    override val success: Boolean,
    @Json(name = "t")
    val loginData: LoginData?
) : BaseResult()