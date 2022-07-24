package ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.spartak.surfandroidschool.presentation.ui.login_screen.LoginScreen
import ru.spartak.surfandroidschool.presentation.ui.login_screen.LoginScreenViewModel
import ru.spartak.surfandroidschool.presentation.ui.main_screen.MainScreen
import ru.spartak.surfandroidschool.presentation.ui.splash_screen.SplashScreen


@Composable
fun ExternalNavGraph(navController: NavHostController, startDestination:String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ExternalScreen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = ExternalScreen.MainScreen.route) {
            MainScreen(externalNavController = navController)
        }
        composable(route = ExternalScreen.SplashScreen.route) {
            SplashScreen()
        }
    }
}