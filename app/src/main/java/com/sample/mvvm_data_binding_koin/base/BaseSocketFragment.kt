package com.sample.mvvm_data_binding_koin.base

import kotlin.reflect.KClass

abstract class BaseSocketFragment<T : BaseViewModel>(clazz: KClass<T>) : BaseFragment<T>(clazz) {

    val receiver by lazy {
        (activity as BaseSocketActivity<*>).receiver
    }

    val service by lazy {
//        (activity as BaseSocketActivity<*>).backService
    }
}