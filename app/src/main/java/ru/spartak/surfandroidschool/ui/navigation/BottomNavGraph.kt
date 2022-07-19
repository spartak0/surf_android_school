package ru.spartak.surfandroidschool.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.spartak.surfandroidschool.ui.favorite_screen.FavoriteScreen
import ru.spartak.surfandroidschool.ui.home_screen.HomeScreen
import ru.spartak.surfandroidschool.ui.profile_screen.ProfileScreen

@Composable
fun BottomNavGraph(navController: NavHostController, bottomPaddingValues: Dp) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItemScreen.Home.route,
        modifier = Modifier.padding(bottom = bottomPaddingValues)
    ) {
        composable(route = BottomBarItemScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarItemScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarItemScreen.Favorite.route) {
            FavoriteScreen()
        }
    }
}