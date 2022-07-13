package ru.spartak.surfandroidschool.ui.navigation

import ru.spartak.surfandroidschool.R

sealed class BottomBarItemScreen(
    val route: String,
    val title:String,
    val icon: Int,
) {
    object Home : BottomBarItemScreen(
        route = "home_route",
        title = "Главная",
        icon= R.drawable.ic_home,
    )

    object Favorite : BottomBarItemScreen(
        route = "favorite_route",
        title = "Избранное",
        icon = R.drawable.ic_favorite
    )

    object Profile : BottomBarItemScreen(
        route = "profile_route",
        title = "Профиль",
        icon = R.drawable.ic_profile
    )
}