package ru.spartak.surfandroidschool.data.database.user_database.dao

import androidx.room.*
import ru.spartak.surfandroidschool.data.database.user_database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE id=:id")
    suspend fun getUser(id: String): UserEntity?

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUser(id: String)
}