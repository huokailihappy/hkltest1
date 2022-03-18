package com.vhall.myapplication.base

/**
 * @author hkl
 *Date: 2022/3/18 4:21 下午
 */
open class BaseViewState {
    object Idle : BaseViewState()
    object Loading : BaseViewState()
    object HintLoading : BaseViewState()
    data class Error(val error: String?) : BaseViewState()
}