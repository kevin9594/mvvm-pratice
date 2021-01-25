package com.sample.mvvm_data_binding_koin

import androidx.multidex.MultiDexApplication
import com.sample.mvvm_data_binding_koin.manager.NetworkStatusManager
import com.sample.mvvm_data_binding_koin.manager.RequestManager


class SampleApp : MultiDexApplication() {
    companion object {
        val TAG = SampleApp::class.java.simpleName
    }
    override fun onCreate() {
        super.onCreate()
        RequestManager.init(this)
        NetworkStatusManager.init(this)
    }
}
