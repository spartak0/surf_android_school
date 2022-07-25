package ru.spartak.surfandroidschool.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.PictureData

interface PictureRepository {
    suspend fun syncFetchPicture():List<PictureData>
    suspend fun fetchPicture():List<PictureData>
    suspend fun fetchFavoritePicture():List<PictureData>
    suspend fun updatePicture(picture: PictureData)
    suspend fun addPicture(picture: PictureData)
    suspend fun getPictureFromNetwork(): Flow<NetworkResult<List<PictureData>>>
    suspend fun getPictureById(id:String): PictureData
}