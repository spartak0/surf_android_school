package ru.spartak.surfandroidschool.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.network.api.RetrofitApi
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.domain.mapper.PictureMapper
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.domain.repository.PictureRepository
import ru.spartak.surfandroidschool.utils.Constants
import java.util.stream.Collectors

class PictureRepositoryImpl(
    private val pictureDao: PictureDao,
    private val pictureMapper: PictureMapper,
    private val api: RetrofitApi,
    private val userSharedPreferenceHelper: UserSharedPreferenceHelper
) : PictureRepository {

    override suspend fun fetchPicture(): List<PictureData> {
        return pictureDao.fetchPictureList().stream().map {
            pictureMapper.entityToDomain(it)
        }.collect(Collectors.toList())
    }

    override suspend fun fetchFavoritePicture(): List<PictureData> {
        return pictureDao.fetchFavoritePictureList().stream().map {
            pictureMapper.entityToDomain(it)
        }.collect(Collectors.toList())
    }

    override suspend fun getPictureFromNetwork(): Flow<NetworkResult<List<PictureData>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val authToken = userSharedPreferenceHelper.loadData(Constants.USER_TOKEN)
            val response = api.getPicture("Token $authToken")
            if (response.isSuccessful) {
                response.body()?.let { list ->
                    emit(
                        NetworkResult.Success(
                            list.stream()
                                .map { picture -> pictureMapper.dtoToDomain(picture) }
                                .collect(Collectors.toList())
                        )
                    )
                }
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            //emit(NetworkResult.Throw(t.message))
        }
    }

    override suspend fun getPictureById(id: String): PictureData {
        return pictureMapper.entityToDomain(pictureDao.getPictureById(id))
    }

    override suspend fun updatePicture(picture: PictureData) {
        pictureDao.updatePicture(pictureMapper.domainToEntity(picture))
    }

    override suspend fun addPicture(picture: PictureData) {
        pictureDao.addPicture(pictureMapper.domainToEntity(picture))
    }
}