package ru.spartak.surfandroidschool.presentation.ui.detail.error_snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorSnackbar(snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier) {
    SnackbarHost(modifier = modifier, hostState = snackbarHostState, snackbar = { data ->
        Snackbar(elevation = 0.dp, modifier = Modifier.background(shape = RectangleShape, color = MaterialTheme.colors.error), backgroundColor = MaterialTheme.colors.error) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = data.message, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500))
            }
        }
    })
}