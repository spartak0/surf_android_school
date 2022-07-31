package ru.spartak.surfandroidschool.data

import android.util.Log
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.network.api.RetrofitApi
import ru.spartak.surfandroidschool.domain.mapper.PictureMapper
import ru.spartak.surfandroidschool.domain.model.PictureData
import java.util.stream.Collectors

class SyncUpManager(
    private val api: RetrofitApi,
    private val pictureDao: PictureDao,
    private val pictureMapper: PictureMapper
) {
    suspend fun syncAll(token: String) {
        val pictureFromDB = pictureDao.getLocalePicture()
        api.getPicture("Token $token").body()?.let {
            val listDB = pictureFromDB.stream().map { entity ->
                pictureMapper.entityToDomain(entity)
            }.collect(Collectors.toList())
            val listNetwork = it.stream().map { pic ->
                pictureMapper.dtoToDomain(pic)
            }.collect(Collectors.toList())
            if (pictureFromDB.isEmpty()) listNetwork.forEach { picData ->
                pictureDao.addPicture(
                    pictureMapper.domainToEntity(
                        picData
                    )
                )
            }
            else {
                Log.d("AAA", "syncAll: network\n$listNetwork")
                Log.d("AAA", "syncAll: db\n $listDB")
                val newList = mutableListOf<PictureData>()
                for (i in listNetwork) {
                    var met = false
                    for (j in listDB) {
                        if (i.id == j.id) {
                            val tmpPicture = i.copy(isFavorite = j.isFavorite)
                            met = true
                            newList.add(tmpPicture)
                        }
                    }
                    if (!met) newList.add(i)
                }
                pictureDao.clearTable()
                newList.forEach { picData ->
                    pictureDao.addPicture(pictureMapper.domainToEntity(picData))
                }

            }
        }
    }

}