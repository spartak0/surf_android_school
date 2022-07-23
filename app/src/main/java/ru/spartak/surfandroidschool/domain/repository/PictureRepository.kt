package ru.spartak.surfandroidschool.domain.repository

import ru.spartak.surfandroidschool.domain.model.PictureData

interface PictureRepository {
    suspend fun fetchPicture():List<PictureData>
    suspend fun updatePicture(picture: PictureData)
    suspend fun getPictureFromNetwork(token: String): List<PictureData>
}