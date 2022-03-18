package com.vhall.myapplication.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vhall.myapplication.model.MainViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    ViewModelContract {

    abstract val mIntent: Channel<BaseUserIntent>



    override fun process() {

    }
}