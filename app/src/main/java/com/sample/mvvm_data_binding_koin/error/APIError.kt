package com.sample.mvvm_data_binding_koin.error

data class APIError(
    val code: Int? = null,
    val msg: String? = null,
    val success: Boolean? = null,
)
