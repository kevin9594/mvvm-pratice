package com.sample.mvvm_data_binding_koin.error

enum class TokenError(val code: Int) {
    FAILURE(1002),
    EXPIRED(1004),
    REPEAT_LOGIN(1005)
}