package ru.spartak.surfandroidschool.data.database.picture_database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.database.picture_database.entity.PictureEntity

@Database(entities = [PictureEntity::class], version = 1)
abstract class PictureDatabase : RoomDatabase() {
    abstract fun pictureDao(): PictureDao
}