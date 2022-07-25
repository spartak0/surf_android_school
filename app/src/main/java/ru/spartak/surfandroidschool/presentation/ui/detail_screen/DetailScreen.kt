package ru.spartak.surfandroidschool.presentation.ui.detail_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.presentation.ui.favorite_screen.toTimeDateString
import ru.spartak.surfandroidschool.presentation.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.presentation.ui.theme.spacing

@Composable
fun DetailScreen(navController: NavController, pictureData: PictureData) {
    DefaultTheme {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.smallMedium
            )) {
            Post(pictureData = pictureData)
        }
    }
}

@Composable
fun Post(pictureData: PictureData) {
    ConstraintLayout {
        val (image, title, content, date) = createRefs()
        GlideImage(
            imageModel = pictureData.photoUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )
        Text(
            text = pictureData.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, 12.dp)
                start.linkTo(image.start)
            })
        Text(
            text = pictureData.content,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.constrainAs(content) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(title.bottom)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = pictureData.publicationDate.toTimeDateString(),
            style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.W500),
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(image.bottom, 17.dp)
                end.linkTo(parent.end)
            }
        )
    }
}