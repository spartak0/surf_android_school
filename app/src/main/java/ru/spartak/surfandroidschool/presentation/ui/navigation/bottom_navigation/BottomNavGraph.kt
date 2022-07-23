package ru.spartak.surfandroidschool.presentation.ui.navigation.bottom_navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.spartak.surfandroidschool.presentation.ui.favorite_screen.FavoriteScreen
import ru.spartak.surfandroidschool.presentation.ui.home_screen.HomeScreen
import ru.spartak.surfandroidschool.presentation.ui.home_screen.HomeViewModel
import ru.spartak.surfandroidschool.presentation.ui.profile_screen.ProfileScreen

@Composable
fun BottomNavGraph(navController: NavHostController, bottomPaddingValues: Dp) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItemScreen.Home.route,
        modifier = Modifier.padding(bottom = bottomPaddingValues)
    ) {
        composable(route = BottomBarItemScreen.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(viewModel)
        }
        composable(route = BottomBarItemScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarItemScreen.Favorite.route) {
            FavoriteScreen()
        }
    }
}