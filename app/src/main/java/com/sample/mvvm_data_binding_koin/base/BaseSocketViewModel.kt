package com.sample.mvvm_data_binding_koin.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.sample.mvvm_data_binding_koin.repository.LoginRepository

abstract class BaseSocketViewModel(
    loginRepository: LoginRepository,
) : BaseViewModel(loginRepository) {

    init {
        if (!loginRepository.isCheckToken) {
            viewModelScope.launch {

            }
        }
    }
}