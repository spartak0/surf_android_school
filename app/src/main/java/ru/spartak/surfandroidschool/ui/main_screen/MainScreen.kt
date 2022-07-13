package ru.spartak.surfandroidschool.ui.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.spartak.surfandroidschool.ui.navigation.BottomBarItemScreen
import ru.spartak.surfandroidschool.ui.navigation.BottomNavGraph
import ru.spartak.surfandroidschool.ui.theme.DefaultTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val screens = listOf(
        BottomBarItemScreen.Home,
        BottomBarItemScreen.Favorite,
        BottomBarItemScreen.Profile,
    )
    DefaultTheme() {
        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController,
                    screens = screens
                )
            }
        ) {
            BottomNavGraph(navController = navController)
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    screens: List<BottomBarItemScreen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp)
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

//@Composable
//fun TopBar(screens: List<BottomBarItemScreen>, navController: NavHostController) {
//    val appBarText = remember { mutableStateOf("") }
//    navController.addOnDestinationChangedListener { _, destination, _ ->
//        run {
//            screens.forEach {
//                if (it.route == destination.route) appBarText.value = it.title
//            }
//        }
//    }
//    TopAppBar(
//        backgroundColor = MaterialTheme.colors.background,
//        modifier = Modifier.height(56.dp),
//        elevation = 0.dp
//    ) {
//        Text(
//            text = appBarText.value,
//            style = MaterialTheme.typography.h1,
//            color = MaterialTheme.colors.primary
//        )
//    }
//}

@Composable
fun RowScope.AddItem(
    screen: BottomBarItemScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title, style = MaterialTheme.typography.subtitle2) },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = { navController.navigate(screen.route) },
        unselectedContentColor = MaterialTheme.colors.onSurface
    )
}