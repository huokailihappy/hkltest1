package com.vhall.myapplication.net

import okhttp3.FormBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author hkl
 *Date: 2022/3/15 3:33 下午
 */
interface IServiceModel {
    /**
     * 用户登陆
     */
    @POST("api/app/user/account-login")
    suspend fun login(@Body body: FormBody?): ResponseJe<String>

}