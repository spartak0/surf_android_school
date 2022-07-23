package ru.spartak.surfandroidschool.data.repository

import android.util.Log
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import ru.spartak.surfandroidschool.data.database.user_database.dao.UserDao
import ru.spartak.surfandroidschool.data.network.api.RetrofitApi
import ru.spartak.surfandroidschool.data.network.dto.LoginRequest
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.domain.mapper.UserMapper
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.UserData
import ru.spartak.surfandroidschool.domain.repository.UserRepository
import ru.spartak.surfandroidschool.utils.Constants

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userMapper: UserMapper,
    private val userSharedPreferenceHelper: UserSharedPreferenceHelper,
    private val api: RetrofitApi,
) : UserRepository {
    override suspend fun login(login: String, password: String): Flow<NetworkResult<UserData>> =
        flow {
            emit(NetworkResult.Loading())
            try {
                val response = api.login(LoginRequest(login, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        userSharedPreferenceHelper.saveData(Constants.USER_TOKEN,it.token)
                        userSharedPreferenceHelper.saveData(Constants.CURRENT_USER_ID,it.user_info.id)
                        emit(NetworkResult.Success(userMapper.dtoToDomain(it.user_info)))
                    }
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (t: Throwable) {
                emit(NetworkResult.Throw(t.message))
            }
        }
//    {
//        val response = api.login(LoginRequest(login, password))
//        return if (response.isSuccessful) {
//            val newResponseBody = response.body()?.let {
//                userMapper.dtoToDomain(it.user_info)
//            }
//            NetworkResult.Success(newResponseBody!!)
//        } else {
//            NetworkResult.Error(response.message())
//        }
//    }

    override suspend fun logout(authToken: String) {
        api.logout(authToken)
    }

    override suspend fun getUser(userId: String): UserData {
        return userMapper.entityToDomain(userDao.getUser(userId))
    }

    override suspend fun updateUser(userData: UserData) {
        userDao.updateUser(userMapper.domainToEntity(userData))
    }

    override suspend fun addUser(userData: UserData) {
        userDao.addUser(userMapper.domainToEntity(userData))
    }
}