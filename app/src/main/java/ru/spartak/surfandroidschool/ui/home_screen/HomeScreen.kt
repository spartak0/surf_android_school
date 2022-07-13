package ru.spartak.surfandroidschool.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.ui.theme.DefaultTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    DefaultTheme {
        Scaffold(topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                modifier = Modifier.height(56.dp),
                title = {
                    Text(
                        text = "Галерея",
                        //style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.primary
                    )
                },
                actions = {
                    //todo наладить падинг иконкии
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search icon",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(end= 13.dp)
                    )
                })
        }) {
            Text(text = "Home", Modifier.padding(start = 16.dp))
        }

    }
}