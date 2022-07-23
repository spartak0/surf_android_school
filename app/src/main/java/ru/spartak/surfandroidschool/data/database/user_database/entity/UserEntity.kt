package ru.spartak.surfandroidschool.data.database.user_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val phone: String,
    val email: String,
    val firstName: String,
    val lastName:String,
    val avatar:String,
    val city: String,
    val about:String
    )
