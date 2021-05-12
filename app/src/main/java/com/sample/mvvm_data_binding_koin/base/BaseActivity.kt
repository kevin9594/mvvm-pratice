package com.sample.mvvm_data_binding_koin.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sample.mvvm_data_binding_koin.CustomAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<T : BaseViewModel>(clazz: KClass<T>) : AppCompatActivity() {

    private var mLayoutHandler = Handler(Looper.getMainLooper())
    private var mPromptDialog: CustomAlertDialog? = null
    private var mTokenPromptDialog: CustomAlertDialog? = null

    val viewModel: T by viewModel(clazz = clazz)

    private var loadingView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onTokenStateChanged()
        onNetworkException()
    }

    private fun onTokenStateChanged() {
        viewModel.errorResultToken.observe(this, Observer {
            showDialogLogout(it.msg)
        })
    }

    private fun showDialogLogout(message: String) {
        showTokenPromptDialog(message) {
            viewModel.doLogoutCleanUser()
        }
    }

    private fun onNetworkException() {
        viewModel.networkExceptionUnknown.observe(this, Observer {
            //TODO show network exception message
        })
    }

    open fun loading() {
        loading(null)
    }

    open fun loading(message: String?) {
    }

    /*关闭加载界面*/
    open fun hideLoading() {
    }

    //隱藏鍵盤
    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onNetworkUnavailable() {
        Toast.makeText(applicationContext, "無網路請重新連線", Toast.LENGTH_SHORT).show()
    }

    private fun showTokenPromptDialog(errorMessage: String, positiveClickListener: () -> Unit?) {
        safelyUpdateLayout(Runnable {
            try {
                //防止跳出多個 error dialog
                if (mTokenPromptDialog?.isShowing == true)
                    return@Runnable

                mTokenPromptDialog = CustomAlertDialog(this@BaseActivity).apply {
                    setMessage(errorMessage)
                    setNegativeButtonText(null)
                    setPositiveClickListener {
                        positiveClickListener()
                        mTokenPromptDialog?.dismiss()
                        mTokenPromptDialog = null
                    }
                    setCanceledOnTouchOutside(false)
                    setCancelable(false) //不能用系統 BACK 按鈕關閉 dialog
                    show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    fun showPromptDialog(title: String, message: String, positiveClickListener: () -> Unit?) {
        showPromptDialog(title, message, null, positiveClickListener, false)
    }

    fun showErrorPromptDialog(title: String, message: String, positiveClickListener: () -> Unit?) {
        showPromptDialog(title, message, null, positiveClickListener, true)
    }

    private fun showPromptDialog(title: String?, errorMessage: String?, buttonText: String?, positiveClickListener: () -> Unit?, isError: Boolean) {
        safelyUpdateLayout(Runnable {
            try {
                //防止跳出多個 error dialog
                if (mPromptDialog?.isShowing == true)
                    mPromptDialog?.dismiss()
                if (mTokenPromptDialog?.isShowing == true) {
                    mPromptDialog?.dismiss()
                    return@Runnable
                }

                mPromptDialog = CustomAlertDialog(this@BaseActivity).apply {
                    setTitle(title)
                    setMessage(errorMessage)
                    setNegativeButtonText(null)
                    setPositiveClickListener {
                        positiveClickListener()
                        mPromptDialog?.dismiss()
                    }

                    setCanceledOnTouchOutside(false)
                    setCancelable(false) //不能用系統 BACK 按鈕關閉 dialog
                    show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    protected fun safelyUpdateLayout(runnable: Runnable) {
        try {
            mLayoutHandler.post(runnable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
