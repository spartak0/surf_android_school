package ru.spartak.surfandroidschool.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.spartak.surfandroidschool.R

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_light, weight = FontWeight.Light),
)

val Typography = Typography(
    defaultFontFamily = montserratFontFamily,
    //top bar
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    //placeholder
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    //bottom navigation title
    subtitle2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp
    ),
    //text tv
    body1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    //button
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    //label tv
    caption = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
)