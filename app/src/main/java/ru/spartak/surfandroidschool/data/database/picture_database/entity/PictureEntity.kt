package ru.spartak.surfandroidschool.data.database.picture_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture_table")
data class PictureEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long,
    val isFavorite:Boolean,
)