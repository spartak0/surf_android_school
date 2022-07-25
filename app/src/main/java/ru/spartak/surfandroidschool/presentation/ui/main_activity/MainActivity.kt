package ru.spartak.surfandroidschool.presentation.ui.main_activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation.ExternalNavGraph
import ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation.ExternalScreen
import ru.spartak.surfandroidschool.presentation.ui.splash_screen.SplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel by viewModels()
            val navController = rememberNavController()
            Handler(Looper.getMainLooper()).postDelayed({
                navController.navigate(if (viewModel.isLogin()) ExternalScreen.MainScreen.route else ExternalScreen.LoginScreen.route){
                    popUpTo(0)
                }
            }, 800)
            ExternalNavGraph(
                navController = navController,
                startDestination = ExternalScreen.SplashScreen.route
            )
        }
    }
}
