package com.sample.mvvm_data_binding_koin


import android.app.Application
import android.content.Context
import com.sample.mvvm_data_binding_koin.manager.NetworkStatusManager
import com.sample.mvvm_data_binding_koin.manager.RequestManager
import com.sample.mvvm_data_binding_koin.repository.LoginRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class SampleApp : Application() {


    companion object {
        lateinit var appContext: Context
    }


    private val viewModelModule = module {
        viewModel { MainViewModel(get(), get()) }
    }


    private val repoModule = module {
        single { LoginRepository(get()) }
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidContext(this@SampleApp)
            modules(
                listOf(
                    viewModelModule,
                    repoModule,
                )
            )
        }
        RequestManager.init(this)
        NetworkStatusManager.init(this)
    }


}
