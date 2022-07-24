package ru.spartak.surfandroidschool.data.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import ru.spartak.surfandroidschool.data.network.dto.AuthInfo
import ru.spartak.surfandroidschool.data.network.dto.LoginRequest
import ru.spartak.surfandroidschool.data.network.dto.Picture
import java.util.*

interface RetrofitApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthInfo>

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String) : Response<Unit>

    @GET("picture")
    suspend fun getPicture(@Header("Authorization") token: String): Response<List<Picture>>
}