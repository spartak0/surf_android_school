package ru.spartak.surfandroidschool.domain.model

data class PictureData(
    val id: String="",
    val title: String="",
    val content: String="",
    val photoUrl: String="",
    val publicationDate: Long=0,
    val isFavorite:Boolean=false,
){

}