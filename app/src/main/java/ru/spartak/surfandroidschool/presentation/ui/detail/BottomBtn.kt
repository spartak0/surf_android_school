package ru.spartak.surfandroidschool.presentation.ui.detail

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun BottomBtn(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RectangleShape
    ) {
        if (isLoading) CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp
        )
        else Text(text = text)
    }
}