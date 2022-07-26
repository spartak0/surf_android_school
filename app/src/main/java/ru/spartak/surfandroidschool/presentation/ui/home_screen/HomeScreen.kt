package ru.spartak.surfandroidschool.presentation.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.presentation.ui.detail.error_snackbar.ErrorSnackbar
import ru.spartak.surfandroidschool.presentation.ui.detail.error_snackbar.SnackbarController
import ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation.ExternalScreen
import ru.spartak.surfandroidschool.presentation.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.presentation.ui.theme.favoriteBtn
import ru.spartak.surfandroidschool.presentation.ui.theme.spacing
import ru.spartak.surfandroidschool.utils.Constants

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    val searchBarState = remember { mutableStateOf(SearchBarState.Dormant) }
    val textSearchBar = remember { mutableStateOf("") }
    val postList by viewModel.pictureDataList.collectAsState()

    viewModel.fetchPicture()
    val scaffoldState = rememberScaffoldState()
    val snackbarController = SnackbarController(CoroutineScope(Dispatchers.IO))
    DefaultTheme {
        Scaffold(
            topBar = {
                HomeTopAppBar(searchFunction = {
                    viewModel.newSearch(textSearchBar.value)
                }, searchText = textSearchBar, searchBarState = searchBarState)
            },
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState })
        {
            if (searchBarState.value == SearchBarState.Active) {
                SearchedScreen(
                    textSearchBar = textSearchBar,
                    viewModel = viewModel,
                    navController = navController
                )
            } else {
                SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = false),
                    onRefresh = { viewModel.fetchPicture() }) {
                    DefaultScreen(
                        navController = navController,
                        viewModel = viewModel,
                        postList = postList
                    )
                }

            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    modifier = Modifier
                        .height(68.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 10.dp)
                )
            }
            if (!viewModel.checkForInternet()) {
                snackbarController.getScope().launch(Dispatchers.IO) {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = navController.context.applicationContext.getString(R.string.noInternet),
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    postList: List<PictureData>
) {
    if (postList.isEmpty()) {
        FullscreenIconHint(
            iconId = R.drawable.ic_sadsmiley,
            textHint = stringResource(id = R.string.empty)
        )
    } else VerticalGrid(
        items = postList,
        postOnClick = { pictureData ->
            run {
                navController.currentBackStackEntry?.arguments?.putAll(
                    bundleOf(
                        Constants.DETAIL_ARGUMENTS_KEY to pictureData
                    )
                )
                navController.navigate(ExternalScreen.DetailScreen.route)
            }
        },
        updatePost = { pictureData -> viewModel.updatePicture(pictureData) }
    )
}

@Composable
fun SearchedScreen(
    textSearchBar: MutableState<String>,
    viewModel: HomeViewModel,
    navController: NavController
) {
    if (textSearchBar.value.isEmpty()) {
        FullscreenIconHint(
            iconId = R.drawable.ic_magnifyingglass,
            textHint = stringResource(R.string.hintEnterRequest)
        )
    } else {
        val searchedList = viewModel.newSearch(textSearchBar.value)
        if (searchedList.isEmpty()) {
            FullscreenIconHint(
                iconId = R.drawable.ic_sadsmiley,
                textHint = stringResource(id = R.string.noResults)
            )
        } else VerticalGrid(
            items = searchedList,
            postOnClick = { pictureData ->
                run {
                    navController.currentBackStackEntry?.arguments?.putAll(
                        bundleOf(
                            Constants.DETAIL_ARGUMENTS_KEY to pictureData
                        )
                    )
                    navController.navigate(ExternalScreen.DetailScreen.route)
                }
            },
            updatePost = { pictureData ->
                viewModel.updatePicture(
                    pictureData
                )
            })
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
    searchBarState: MutableState<SearchBarState>,
    searchText: MutableState<String>,
    searchFunction: () -> Unit
) {
    if (searchBarState.value == SearchBarState.Dormant)
        DefaultTopBar(searchBarState = searchBarState, searchText = searchText)
    else SearchTopBar(
        searchBarState = searchBarState,
        searchFunction = searchFunction,
        searchText = searchText
    )
}

@Composable
fun SearchTopBar(
    searchBarState: MutableState<SearchBarState>,
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
        IconButton(onClick = { searchBarState.value = SearchBarState.Dormant }) {
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
fun VerticalGrid(
    items: List<PictureData>,
    updatePost: (PictureData) -> Unit,
    postOnClick: (PictureData) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = MaterialTheme.spacing.smallMedium,
            bottom = MaterialTheme.spacing.smallMedium,
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.medium
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Post(item, updatePost, postOnClick)
        }
    }
}

@Composable
fun DefaultTopBar(
    searchBarState: MutableState<SearchBarState>,
    searchText: MutableState<String>
) {
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

                searchBarState.value = SearchBarState.Active
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
fun Post(
    pictureData: PictureData,
    updatePost: (PictureData) -> Unit,
    postOnClick: (PictureData) -> Unit
) {
    ConstraintLayout(Modifier.clickable { postOnClick(pictureData) }) {
        val (image, like, title) = createRefs()
        val spacing = MaterialTheme.spacing
        val isFavorite = remember { mutableStateOf(pictureData.isFavorite) }
        GlideImage(
            imageModel = pictureData.photoUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(0.72F)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
        )
        IconButton(onClick = {
            isFavorite.value = !isFavorite.value
            val newPictureData = pictureData.copy(isFavorite = isFavorite.value)
            updatePost(newPictureData)

        },
            modifier = Modifier.constrainAs(like) {
                top.linkTo(image.top, spacing.small)
                end.linkTo(image.end, spacing.small)
            }) {
            Icon(
                painter = painterResource(id = if (isFavorite.value) R.drawable.ic_favorite else R.drawable.ic_not_favorite),
                contentDescription = "favorite",
                tint = favoriteBtn
            )
        }
        Text(
            text = pictureData.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, 6.dp)
                start.linkTo(image.start)
            })
    }
}
