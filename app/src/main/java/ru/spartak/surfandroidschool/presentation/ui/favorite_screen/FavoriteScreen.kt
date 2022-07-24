package ru.spartak.surfandroidschool.presentation.ui.favorite_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.presentation.ui.detail.Dialog
import ru.spartak.surfandroidschool.presentation.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.presentation.ui.theme.favoriteBtn
import ru.spartak.surfandroidschool.presentation.ui.theme.spacing
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    val showDialog= remember{mutableStateOf(Pair(false, PictureData()))}
    val favoritePictureList = viewModel.favoritePictureList.collectAsState()
    viewModel.fetchFavoritePicture()

    DefaultTheme {
        Scaffold(topBar = { FavoriteTopBar() }) {
            VerticalList(
                items = favoritePictureList.value,
                dialogState = showDialog,
            )
            if (showDialog.value.first)
                Dialog(
                    showDialogState = showDialog.value.first,
                    dismissRequest = { showDialog.value = Pair(false,PictureData()) },
                    dismissButtonText = "НЕТ",
                    confirmRequest = {
                        val newPictureValue = showDialog.value.second.copy(isFavorite = false)
                        viewModel.updatePicture(newPictureValue)
                        showDialog.value = Pair(false,PictureData())
                    },
                    confirmButtonText = "ДА",
                    text = "Вы точно хотите удалить из избранного?"
                )
        }
    }
}


@Composable
fun FavoriteTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.myFavorite)) },
        modifier = Modifier.height(56.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun VerticalList(items: List<PictureData>, dialogState: MutableState<Pair<Boolean,PictureData>>) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = MaterialTheme.spacing.smallMedium,
            horizontal = MaterialTheme.spacing.medium
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        items(items = items) { item ->
            Post(item,dialogState)
        }
    }
}

@Composable
fun Post(pictureData: PictureData, dialogState: MutableState<Pair<Boolean,PictureData>>) {
    ConstraintLayout {
        val (image, like, title,content,date) = createRefs()
        val spacing = MaterialTheme.spacing
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
        IconButton(onClick = {
            dialogState.value = Pair(true, pictureData)
        },
            modifier = Modifier.constrainAs(like) {
                top.linkTo(image.top, spacing.small)
                end.linkTo(image.end, spacing.small)
            }) {
            Icon(
                painter = painterResource(id = if (pictureData.isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite),
                contentDescription = "favorite",
                tint = favoriteBtn
            )
        }
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
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.constrainAs(content){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(title.bottom)
                width=Dimension.fillToConstraints
            }
        )
        Text(
            text = pictureData.publicationDate.toTimeDateString(),
            style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.W500),
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            modifier = Modifier.constrainAs(date){
                top.linkTo(image.bottom,17.dp)
                end.linkTo(parent.end)
            }
        )
    }
}

fun Long.toTimeDateString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    return format.format(dateTime)
}