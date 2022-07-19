package ru.spartak.surfandroidschool.domain.model

data class Picture(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: String,
    val isFavorite:Boolean = false,
)