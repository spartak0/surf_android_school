package ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.spartak.surfandroidschool.presentation.ui.login_screen.LoginScreen
import ru.spartak.surfandroidschool.presentation.ui.login_screen.LoginScreenViewModel
import ru.spartak.surfandroidschool.presentation.ui.main_screen.MainScreen


@Composable
fun ExternalNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ExternalScreen.LoginScreen.route
    ) {
        composable(route = ExternalScreen.LoginScreen.route) {
            val loginViewModel = hiltViewModel<LoginScreenViewModel>()
            LoginScreen(loginViewModel, navController)
        }
        composable(route = ExternalScreen.MainScreen.route) {
            MainScreen()
        }
    }
}