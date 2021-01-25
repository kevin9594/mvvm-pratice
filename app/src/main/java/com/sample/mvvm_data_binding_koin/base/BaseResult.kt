package com.sample.mvvm_data_binding_koin.base

abstract class BaseResult {
    abstract val code: Int
    abstract val msg: String
    abstract val success: Boolean
}