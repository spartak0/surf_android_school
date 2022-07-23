package ru.spartak.surfandroidschool.data.database.user_database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.spartak.surfandroidschool.data.database.user_database.dao.UserDao
import ru.spartak.surfandroidschool.data.database.user_database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class UserDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
}