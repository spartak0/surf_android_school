package ru.spartak.surfandroidschool.presentation.ui.favorite_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.presentation.ui.detail.Dialog
import ru.spartak.surfandroidschool.presentation.ui.home_screen.FullscreenIconHint
import ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation.ExternalScreen
import ru.spartak.surfandroidschool.presentation.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.presentation.ui.theme.favoriteBtn
import ru.spartak.surfandroidschool.presentation.ui.theme.spacing
import ru.spartak.surfandroidschool.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel(), navController:NavController) {
    val showDialog = remember { mutableStateOf(Pair(false, PictureData())) }
    val favoritePictureList = viewModel.favoritePictureList.collectAsState()
    viewModel.fetchFavoritePicture()

    DefaultTheme {
        Scaffold(topBar = { FavoriteTopBar() }) {
            if (favoritePictureList.value.isEmpty())
                FullscreenIconHint(
                    iconId = R.drawable.ic_sadsmiley,
                    textHint = stringResource(R.string.empty)
                )
            else VerticalList(
                items = favoritePictureList.value,
                dialogState = showDialog,
                postOnClick = { pictureData ->
                    run {
                        navController.currentBackStackEntry?.arguments?.putAll(
                            bundleOf(
                                Constants.DETAIL_ARGUMENTS_KEY to pictureData
                            )
                        )
                        navController.navigate(ExternalScreen.DetailScreen.route)
                    }
                }
            )
            if (showDialog.value.first)
                Dialog(
                    showDialogState = showDialog.value.first,
                    dismissRequest = { showDialog.value = Pair(false, PictureData()) },
                    dismissButtonText = stringResource(R.string.no),
                    confirmRequest = {
                        val newPictureValue = showDialog.value.second.copy(isFavorite = false)
                        viewModel.updatePicture(newPictureValue)
                        showDialog.value = Pair(false, PictureData())
                    },
                    confirmButtonText = stringResource(R.string.yes),
                    text = stringResource(R.string.removeFavorite)
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
fun VerticalList(
    items: List<PictureData>,
    dialogState: MutableState<Pair<Boolean, PictureData>>,
    postOnClick: (PictureData) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = MaterialTheme.spacing.smallMedium,
            horizontal = MaterialTheme.spacing.medium
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        items(items = items) { item ->
            Post(item, dialogState, postOnClick = postOnClick)
        }
    }
}

@Composable
fun Post(
    pictureData: PictureData,
    dialogState: MutableState<Pair<Boolean, PictureData>>,
    postOnClick: (PictureData) -> Unit
) {
    ConstraintLayout(Modifier.clickable { postOnClick(pictureData) }) {
        val (image, like, title, content, date) = createRefs()
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
                contentDescription = null,
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

fun Long.toTimeDateString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    return format.format(dateTime)
}