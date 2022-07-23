package ru.spartak.surfandroidschool.data.database.user_database.dao

import androidx.room.*
import ru.spartak.surfandroidschool.data.database.user_database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE ID=:userId")
    suspend fun getUser(userId: String): UserEntity

    @Update
    suspend fun updateUser(userEntity: UserEntity)
}