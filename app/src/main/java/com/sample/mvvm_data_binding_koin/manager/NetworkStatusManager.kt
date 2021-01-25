package com.sample.mvvm_data_binding_koin.manager

import android.content.Context
import com.sample.mvvm_data_binding_koin.util.NetworkUtil


class NetworkStatusManager private constructor(val context: Context) {

    companion object {

        private var instance: NetworkStatusManager? = null
        private var context: Context? = null

        fun init(c: Context) {
            context = c
        }

        fun getInstance(): NetworkStatusManager {
            if (context == null) {
                throw RuntimeException("You must initialize this manager before getting instance")
            }
            if (instance == null) {
                instance =
                    NetworkStatusManager(
                        context!!
                    )
            }
            return instance!!
        }

    }

    fun isUnavailable(): Boolean {
        return !NetworkUtil.isAvailable(context)
    }

}