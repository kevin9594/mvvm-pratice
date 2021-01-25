package com.sample.mvvm_data_binding_koin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sample.mvvm_data_binding_koin.base.BaseActivity
import com.sample.mvvm_pratice.R
import com.sample.mvvm_pratice.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.apply {
            mainViewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
    }
}