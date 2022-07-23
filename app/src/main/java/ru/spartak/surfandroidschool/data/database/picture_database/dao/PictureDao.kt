package ru.spartak.surfandroidschool.data.database.picture_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import ru.spartak.surfandroidschool.data.database.picture_database.entity.PictureEntity

@Dao
interface PictureDao {

    @Query("SELECT * FROM picture_table")
    suspend fun fetchPictureList(): List<PictureEntity>

    @Update
    suspend fun updateUser(user: PictureEntity)
}