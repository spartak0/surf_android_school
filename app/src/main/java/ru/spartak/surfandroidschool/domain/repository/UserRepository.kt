package ru.spartak.surfandroidschool.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.UserData

interface UserRepository {
    suspend fun login(login: String, password: String): Flow<NetworkResult<UserData>>
    suspend fun logout(): Flow<NetworkResult<Unit>>
    suspend fun getCurrentUser(): UserData?
    suspend fun updateUser(userData: UserData)
    suspend fun addUser(userData: UserData)
}