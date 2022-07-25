package ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation

sealed class ExternalScreen(val route:String){
    object SplashScreen:ExternalScreen("splash_screen")
    object LoginScreen:ExternalScreen("login_screen")
    object MainScreen:ExternalScreen("main_screen")
    object DetailScreen:ExternalScreen("detail_screen")

}