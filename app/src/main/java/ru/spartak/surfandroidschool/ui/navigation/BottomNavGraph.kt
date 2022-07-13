package ru.spartak.surfandroidschool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.spartak.surfandroidschool.ui.favorite_screen.FavoriteScreen
import ru.spartak.surfandroidschool.ui.home_screen.HomeScreen
import ru.spartak.surfandroidschool.ui.profile_screen.ProfileScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarItemScreen.Home.route){
        composable(route = BottomBarItemScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarItemScreen.Profile.route){
            ProfileScreen()
        }
        composable(route = BottomBarItemScreen.Favorite.route){
            FavoriteScreen()
        }
    }
}