package com.sample.mvvm_data_binding_koin

import com.sample.mvvm_data_binding_koin.manager.RequestManager

object TestApi {

    val retrofit by lazy {
        RequestManager.instance.retrofit
    }

    val indexService: IndexService by lazy {
        RequestManager.instance
            .retrofit
            .create(IndexService::class.java)
    }

}