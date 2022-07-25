package ru.spartak.surfandroidschool.data.database.picture_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.spartak.surfandroidschool.data.database.picture_database.entity.PictureEntity

@Dao
interface PictureDao {

    @Query("SELECT * FROM picture_table")
    suspend fun getLocalePicture(): List<PictureEntity>

    @Query("SELECT * FROM picture_table WHERE isFavorite=1")
    suspend fun getFavoritePictureList(): List<PictureEntity>

    @Query ("SELECT * FROM picture_table WHERE id=:id")
    suspend fun getPictureById(id:String):PictureEntity

    @Insert
    suspend fun addPicture(picture: PictureEntity)

    @Query("DELETE FROM picture_table")
    fun clearTable()

    @Update
    suspend fun updatePicture(picture: PictureEntity)

}