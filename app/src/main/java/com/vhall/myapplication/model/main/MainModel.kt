package com.vhall.myapplication.model

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.vhall.myapplication.base.BaseUserIntent
import com.vhall.myapplication.base.BaseViewModel
import com.vhall.myapplication.base.BaseViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * @author hkl
 *Date: 2022/3/17 3:55 下午
 */
class MainModel(application: Application) : BaseViewModel(application) {

    val mIntent = Channel<MainIntent>()
    val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val _DialogState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val mainState: StateFlow<MainViewState>
        get() = _state

    val DialogState: StateFlow<MainViewState>
        get() = _DialogState

    init {
        viewModelScope.launch {
            mIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetListData -> fetchNews(1)
                    is MainIntent.login -> login(it.page)
                }
            }
        }
    }

    private fun login(int: Int) {
        _state.value = MainViewState.Loading
    }

    private fun fetchNews(int: Int) {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            delay(1000)
            _state.value = try {
                MainViewState.News(int).getList()
            } catch (e: Exception) {
                MainViewState.Error(e.localizedMessage)
            }
            delay(1000)
            _state.value = MainViewState.HintLoading
        }
    }
}

sealed class MainIntent : BaseUserIntent() {
    object GetListData : MainIntent()
    object JumpCreate : MainIntent()
    data class login(val page: Int) : MainIntent() {
        var name: String = "ni"
        suspend fun getList(): login {
            name = "hhh"
            return this
        }
    }

}

// 2、ViewState
sealed class MainViewState : BaseViewState() {

    object Idle : MainViewState()
    object Loading : MainViewState()
    object HintLoading : MainViewState()
    data class Error(val error: String?) : MainViewState()

    data class News(val page: Int) : MainViewState() {
        var name: String = "ni"
        suspend fun getList(): News {
            name = "hhh"
            return this
        }
    }
}

//冒泡

fun sort(num: IntArray): IntArray {
    val length = num.size
    for (i in 0 until length - 1) {
        var pos = 0
        for (j in 0 until length - i - 1) {
            val temp = num[j]
            if (num[j] > num[j + 1]) {
                num[j] = num[j + 1]
                num[j + 1] = temp
                pos = 1
            }
        }
        if (pos == 0) {
            return num
        }
    }
    return num
}
