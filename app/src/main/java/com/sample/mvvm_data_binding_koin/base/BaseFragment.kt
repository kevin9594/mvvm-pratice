package com.sample.mvvm_data_binding_koin.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

open class BaseFragment<T : BaseViewModel>(clazz: KClass<T>) : Fragment() {

    val viewModel: T by sharedViewModel(clazz = clazz)

    fun onNetworkUnavailable() {
        Toast.makeText(context, "請先連上網路", Toast.LENGTH_SHORT).show()
    }

}
