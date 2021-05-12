package com.sample.mvvm_data_binding_koin.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

open class BaseFragment<T : BaseViewModel>(clazz: KClass<T>) : Fragment() {


    val viewModel: T by sharedViewModel(clazz = clazz)


    open fun loading() {
        loading(null)
    }


    open fun loading(message: String?) {
        if (activity is BaseActivity<*>)
            (activity as BaseActivity<*>).loading(message)
    }


    open fun hideLoading() {
        if (activity is BaseActivity<*>)
            (activity as BaseActivity<*>).hideLoading()
    }


    private fun hideKeyboard() {
        try {
            //*隱藏軟鍵盤
            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val focusedView = activity?.currentFocus
            if (inputMethodManager.isActive && focusedView != null) {
                inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun clearFocus() {
        activity?.currentFocus?.clearFocus()
    }


    protected fun modifyFinish() {
        hideKeyboard()
        clearFocus()
    }


    fun showPromptDialog(title: String, message: String, positiveClickListener: () -> Unit) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showPromptDialog(title, message, positiveClickListener)
        }
    }


    fun showErrorPromptDialog(title: String, message: String, positiveClickListener: () -> Unit) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showErrorPromptDialog(title, message, positiveClickListener)
        }
    }


    fun showPromptDialog(title: String, message: String, success: Boolean, positiveClickListener: () -> Unit) {
        if (activity is BaseActivity<*>) {
            if (success) {
                (activity as BaseActivity<*>).showPromptDialog(title, message, positiveClickListener)
            } else {
                (activity as BaseActivity<*>).showErrorPromptDialog(title, message, positiveClickListener)
            }
        }
    }


    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }


    fun onNetworkUnavailable() {
        Toast.makeText(activity, "請重新連線", Toast.LENGTH_SHORT).show()
    }


}
