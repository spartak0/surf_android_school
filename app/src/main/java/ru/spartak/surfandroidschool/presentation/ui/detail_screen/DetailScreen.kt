package ru.spartak.surfandroidschool.presentation.ui.detail_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.presentation.ui.favorite_screen.toTimeDateString
import ru.spartak.surfandroidschool.presentation.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.presentation.ui.theme.spacing

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, pictureData: PictureData) {
    val scrollState = rememberScrollState()
    DefaultTheme {
        Scaffold(topBar = { DetailTopBar { navController.navigateUp() } }) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.smallMedium
                    )
            ) {
                Post(pictureData = pictureData)
            }
        }
    }
}

@Composable
fun DetailTopBar(backNavigation: () -> Unit) {
    TopAppBar(
        modifier = Modifier.height(56.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        IconButton(onClick = { backNavigation() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_backarrow),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        Text(
            text = stringResource(R.string.gallery),
            style = TextStyle(fontWeight = FontWeight.W600, fontSize = 24.sp),
            color = MaterialTheme.colors.primary
        )
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