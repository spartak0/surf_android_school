package ru.spartak.surfandroidschool.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.spartak.surfandroidschool.data.network.dto.AuthInfo
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.UserData

interface UserRepository {
    suspend fun login(login:String, password:String): Flow<NetworkResult<UserData>>
    suspend fun logout(authToken:String)
    suspend fun getUser(userId:String):UserData
    suspend fun updateUser(userData:UserData)
    suspend fun addUser(userData:UserData)
}