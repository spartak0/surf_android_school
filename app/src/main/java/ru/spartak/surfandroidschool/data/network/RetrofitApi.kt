package ru.spartak.surfandroidschool.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.spartak.surfandroidschool.data.entity.AuthInfo
import ru.spartak.surfandroidschool.data.entity.LoginRequest

interface RetrofitApi {
    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthInfo>
}