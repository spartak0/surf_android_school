package ru.spartak.surfandroidschool.domain.model

data class UserData(
    val id: String = "",
    val phone: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    val city: String = "",
    val about: String = "",
)