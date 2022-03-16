package com.vhall.myapplication.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * @author hkl
 *Date: 2022/3/15 3:32 下午
 */
class HttpManger {
    companion object {
        private var _apiService: IServiceModel? = null
        val apiService
            get() = _apiService
        private var okHttpClient: OkHttpClient? = null
        var BASE_URL: String? = null
    }

    private val timeout: Long = 15
    private fun initOkHttpClient() {
        val builder = OkHttpClient.Builder()
        okHttpClient= builder.also {
            it.connectTimeout(timeout,TimeUnit.SECONDS)
        }.build()
    }
    private fun initRetrofit() {
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build().also {
                    _apiService = it.create(IServiceModel::class.java)
                }
    }

}