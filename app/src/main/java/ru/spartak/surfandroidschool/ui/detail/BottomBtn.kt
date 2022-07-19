package ru.spartak.surfandroidschool.ui.detail

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import ru.spartak.surfandroidschool.R

@Composable
fun BottomBtn(modifier: Modifier = Modifier,text:String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RectangleShape
    ) {
        Text(text = text)
    }
}