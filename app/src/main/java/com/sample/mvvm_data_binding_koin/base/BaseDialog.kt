package com.sample.mvvm_data_binding_koin.base

import android.widget.Toast
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

open class BaseDialog<T : BaseViewModel>(clazz: KClass<T>) : DialogFragment() {

    val viewModel: T by sharedViewModel(clazz = clazz)

    init {
        //隱藏鍵盤 dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        //設定style setStyle(int)
    }

    protected fun setStyle (style: Int) {
        setStyle(STYLE_NO_TITLE, style)
    }


    fun onNetworkUnavailable() {
        Toast.makeText(context, "請先連上網路", Toast.LENGTH_SHORT).show()
    }

}
