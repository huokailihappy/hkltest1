package com.vhall.myapplication.test

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.vhall.myapplication.databinding.BaseListDialogUserBinding
import com.vhall.myapplication.model.MainIntent
import com.vhall.myapplication.model.MainModel
import com.vhall.myapplication.model.MainViewState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author hkl
 *Date: 2022/3/16 6:07 下午
 */
class MyDialog(context: Context, cancelable: Boolean = false) : Dialog(context, cancelable, null) {
    lateinit var viewModel: MainModel
    private lateinit var binding: BaseListDialogUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BaseListDialogUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cancel.setOnClickListener {
            dismiss()
        }
        (context as ComponentActivity).lifecycle
            viewModel = MainModel((context as ComponentActivity).application)

    }

    private fun getList() {
        runBlocking {
            launch {
                viewModel.mIntent.send(MainIntent.login(1))
            }
        }
    }

    private fun observeViewModel() {
        runBlocking {
            launch {
                viewModel.DialogState.collect {
                    when (it) {
                        is MainViewState.Idle -> {
                            binding.cancel.text = "Idle"
                        }
                        is MainViewState.Loading -> {
                            binding.cancel.text = "Loading"
                        }
                    }
                }
            }
        }
    }
}