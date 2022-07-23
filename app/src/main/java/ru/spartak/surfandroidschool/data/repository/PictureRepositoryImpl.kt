package ru.spartak.surfandroidschool.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.network.api.RetrofitApi
import ru.spartak.surfandroidschool.domain.mapper.PictureMapper
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.domain.repository.PictureRepository
import java.util.stream.Collectors

class PictureRepositoryImpl(
    private val pictureDao: PictureDao,
    private val pictureMapper: PictureMapper,
    private val api: RetrofitApi,
) : PictureRepository {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun fetchPicture(): List<PictureData> {
        return pictureDao.fetchPictureList().stream().map {
            pictureMapper.entityToDomain(it)
        }.collect(Collectors.toList())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getPictureFromNetwork(token: String): List<PictureData> {
        return api.getPicture(token).body()?.stream()?.map {
            pictureMapper.dtoToDomain(it)
        }?.collect(Collectors.toList()) ?: listOf()
    }

    override suspend fun updatePicture(picture: PictureData) {

    }
}