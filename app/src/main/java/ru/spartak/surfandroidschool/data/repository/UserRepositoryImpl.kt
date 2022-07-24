package ru.spartak.surfandroidschool.data.repository

import kotlinx.coroutines.flow.Flow
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
                        userSharedPreferenceHelper.saveData(Constants.USER_TOKEN, it.token)
                        userSharedPreferenceHelper.saveData(
                            Constants.CURRENT_USER_ID,
                            it.user_info.id
                        )
                        emit(NetworkResult.Success(userMapper.dtoToDomain(it.user_info)))
                    }
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (t: Throwable) {
                emit(NetworkResult.Throw(t.message))
            }
        }


    override suspend fun logout(): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading())
        try {
            val authTokens = userSharedPreferenceHelper.loadData(Constants.USER_TOKEN)
            val currentUserId = userSharedPreferenceHelper.loadData(Constants.CURRENT_USER_ID)
            val response = api.logout("Token $authTokens")
            if (response.isSuccessful) {
                emit(NetworkResult.Success(Unit))
                if (currentUserId != null) {
                    userDao.deleteUser(currentUserId)
                }
                userSharedPreferenceHelper.clear()
            } else emit(NetworkResult.Error(response.message()))
        } catch (t: Throwable) {
            emit(NetworkResult.Throw(t.message))
        }
    }

    override suspend fun getCurrentUser(): UserData? {
        val currentUserId = userSharedPreferenceHelper.loadData(Constants.CURRENT_USER_ID)
        val currentUser = currentUserId?.let { userMapper.entityToDomain(userDao.getUser(it)) }
        return currentUser
    }

    override suspend fun updateUser(userData: UserData) {
        userDao.updateUser(userMapper.domainToEntity(userData))
    }

    override suspend fun addUser(userData: UserData) {
        userDao.addUser(userMapper.domainToEntity(userData))
    }
}