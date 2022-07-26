package ru.spartak.surfandroidschool.presentation.ui.navigation.internal_navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.presentation.ui.detail.error_snackbar.SnackbarController
import ru.spartak.surfandroidschool.presentation.ui.detail_screen.DetailScreen
import ru.spartak.surfandroidschool.presentation.ui.favorite_screen.FavoriteScreen
import ru.spartak.surfandroidschool.presentation.ui.home_screen.HomeScreen
import ru.spartak.surfandroidschool.presentation.ui.profile_screen.ProfileScreen
import ru.spartak.surfandroidschool.utils.Constants

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    externalNavHostController: NavHostController,
    bottomPaddingValues: Dp
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItemScreen.Home.route,
        modifier = Modifier.padding(bottom = bottomPaddingValues)
    ) {
        composable(route = BottomBarItemScreen.Home.route) {
            HomeScreen(navController = externalNavHostController)
        }
        composable(route = BottomBarItemScreen.Profile.route) {
            ProfileScreen(
                externalNavController = externalNavHostController,
                internalNavHostController = navController
            )
        }
        composable(route = BottomBarItemScreen.Favorite.route) {
            FavoriteScreen()
        }
    }
}