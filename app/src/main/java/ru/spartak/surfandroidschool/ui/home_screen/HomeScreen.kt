package ru.spartak.surfandroidschool.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.domain.model.Picture
import ru.spartak.surfandroidschool.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.ui.theme.spacing

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {

    viewModel.addPicture()
    val searchState = remember { mutableStateOf(SearchState.Dormant) }
    val textSearchBar = remember { mutableStateOf("") }
    val postList by viewModel.pictureList.collectAsState()

    DefaultTheme {
        Scaffold(topBar = {
            HomeTopAppBar(searchFunction = {
                viewModel.newSearch(textSearchBar.value)
            }, searchText = textSearchBar, searchState = searchState)
        }) {
            if (searchState.value == SearchState.Active) {
                if (textSearchBar.value.isEmpty()) {
                    FullscreenIconHint(
                        iconId = R.drawable.ic_magnifyingglass,
                        textHint = "Введите ваш запрос"
                    )
                } else {
                    val searchedList = viewModel.newSearch(textSearchBar.value)
                    if (searchedList.isEmpty()) {
                        FullscreenIconHint(
                            iconId = R.drawable.ic_sadsmiley,
                            textHint = "По этому запросу нет результатов,\n" +
                                    "попробуйте другой запрос"
                        )
                    } else VerticalGrid(
                        items = searchedList,
                        updatePictureInDatabase = { viewModel.updatePicture() })
                }
            } else VerticalGrid(
                items = postList,
                updatePictureInDatabase = { viewModel.updatePicture() })

        }
    }
}

@Composable
fun FullscreenIconHint(iconId: Int, textHint: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = iconId),
                tint = MaterialTheme.colors.onSurface,
                contentDescription = "sad smiley"
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = textHint,
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}

@Composable
fun HomeTopAppBar(
    searchState: MutableState<SearchState>,
    searchText: MutableState<String>,
    searchFunction: () -> Unit
) {
    if (searchState.value == SearchState.Dormant)
        DefaultTopBar(searchState = searchState, searchText = searchText)
    else SearchTopBar(
        searchState = searchState,
        searchFunction = searchFunction,
        searchText = searchText
    )
}

@Composable
fun SearchTopBar(
    searchState: MutableState<SearchState>,
    searchText: MutableState<String>,
    searchFunction: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    TopAppBar(
        modifier = Modifier.height(56.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    ) {
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        IconButton(onClick = { searchState.value = SearchState.Dormant }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_backarrow),
                contentDescription = "back arrow search",
                tint = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        BasicTextField(
            value = searchText.value, onValueChange = { searchText.value = it },
            modifier = Modifier
                .background(MaterialTheme.colors.surface, RoundedCornerShape(22.dp))
                .height(38.dp)
                .weight(1f),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search icon",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.smallMedium))
                    Box {
                        if (searchText.value.isEmpty()) {
                            Text(
                                "Поиск",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    color = MaterialTheme.colors.onSurface
                                )
                            )
                        }
                        innerTextField()
                    }

                }
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                searchFunction()
                focusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
    }
}

@Composable
fun VerticalGrid(items: List<Picture>, updatePictureInDatabase: () -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = MaterialTheme.spacing.smallMedium,
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.medium
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Post(item, updatePictureInDatabase)
        }
    }
}

@Composable
fun DefaultTopBar(searchState: MutableState<SearchState>, searchText: MutableState<String>) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp),
        title = {
            Text(
                text = "Галерея",
                color = MaterialTheme.colors.primary
            )
        },
        actions = {
            IconButton(onClick = {
                searchText.value = ""

                searchState.value = SearchState.Active
            }, modifier = Modifier.padding(end = 16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search icon",
                    tint = MaterialTheme.colors.primary,
                )
            }
        })
}

@Composable
fun Post(picture: Picture, updatePicture: () -> Unit) {
    ConstraintLayout {
        val (image, like, title) = createRefs()
        val spacing = MaterialTheme.spacing
        val isFavorite = remember { mutableStateOf(picture.isFavorite) }
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(0.72F)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            contentDescription = "image on post"
        )
        IconButton(onClick = {
            isFavorite.value = !isFavorite.value
            updatePicture()

        },
            modifier = Modifier.constrainAs(like) {
                top.linkTo(image.top, spacing.smallMedium)
                end.linkTo(image.end, spacing.smallMedium)
            }) {
            Icon(
                painter = painterResource(id = if (isFavorite.value) R.drawable.ic_favorite else R.drawable.ic_not_favorite),
                contentDescription = "favorite",
                tint = MaterialTheme.colors.onSurface
            )
        }
        Text(
            text = picture.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, 6.dp)
                start.linkTo(image.start)
            })
    }
}