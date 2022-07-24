package ru.spartak.surfandroidschool.presentation.ui.profile_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.presentation.ui.detail.BottomBtn
import ru.spartak.surfandroidschool.presentation.ui.detail.Dialog
import ru.spartak.surfandroidschool.presentation.ui.navigation.external_navigation.ExternalScreen
import ru.spartak.surfandroidschool.presentation.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.presentation.ui.theme.montserratFontFamily
import ru.spartak.surfandroidschool.presentation.ui.theme.spacing

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), externalNavController: NavHostController, internalNavHostController: NavHostController) {

    val user = viewModel.user.collectAsState()
    val logoutState = viewModel.logoutState.collectAsState()

    viewModel.fetchUser()
    DefaultTheme {
        Scaffold(topBar = { TopBarProfile() }) {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
            ) {
                val showDialog = remember { mutableStateOf(false) }
                val spacing = MaterialTheme.spacing
                val (imageProfile, fullName, status, city, number, email, signOutBtn) = createRefs()

                GlideImage(
                    imageModel = user.value.avatar,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .constrainAs(imageProfile) {
                            top.linkTo(parent.top, spacing.smallMedium)
                            start.linkTo(parent.start, spacing.medium)
                        }
                )
                FullName(
                    firstname = user.value.firstName,
                    lastname = user.value.lastName,
                    modifier = Modifier.constrainAs(fullName) {
                        start.linkTo(imageProfile.end, spacing.medium)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, spacing.smallMedium)
                        width = Dimension.fillToConstraints
                    })
                Status(text = "“${user.value.about}”",
                    modifier = Modifier.constrainAs(status) {
                        top.linkTo(fullName.bottom, spacing.smallMediumMedium)
                        start.linkTo(imageProfile.end, spacing.medium)
                        end.linkTo(parent.end, spacing.large)
                        width = Dimension.fillToConstraints
                    })
                ItemInfoCard(
                    text = user.value.city,
                    label = "Город",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(city) {
                            top.linkTo(imageProfile.bottom, spacing.smallMedium)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                ItemInfoCard(
                    text = user.value.phone,
                    label = "Телефон",
                    isNumber = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(number) {
                            top.linkTo(city.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                ItemInfoCard(
                    text = user.value.email,
                    label = "Почта",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(email) {
                            top.linkTo(number.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                BottomBtn(
                    text = "Выйти", modifier = Modifier
                        .background(Color.Black)
                        .constrainAs(signOutBtn) {
                            bottom.linkTo(parent.bottom, 20.dp)
                            start.linkTo(parent.start, spacing.medium)
                            end.linkTo(parent.end, spacing.medium)
                            width = Dimension.fillToConstraints
                            height = Dimension.value(48.dp)
                        },
                    isLoading = logoutState.value is NetworkResult.Loading
                ) {
                    showDialog.value = true
                }
                if (showDialog.value)
                    Dialog(
                        showDialogState = showDialog.value,
                        dismissRequest = { showDialog.value = false },
                        dismissButtonText = "НЕТ",
                        confirmRequest = {
                            showDialog.value = false
                            viewModel.logout(
                                onSuccess = {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        externalNavController.navigate(ExternalScreen.LoginScreen.route) {
                                            popUpTo(0)
                                        }
                                    }
                                },
                                onError = {
                                    Log.d("AAA", "ProfileScreen: $it")
                                })
                        },
                        confirmButtonText = "ДА",
                        text = "Вы точно хотите выйти из приложения?"
                    )
            }

        }
    }
}

@Composable
fun ItemInfoCard(
    text: String,
    label: String,
    isNumber: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        shape = RectangleShape
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (isNumber && text.length == 12) "${
                    text.substring(
                        0,
                        2
                    )
                } (${text.substring(2, 5)}) ${text.substring(5, 8)} ${
                    text.substring(8, 10)
                } ${text.substring(10, 12)}" else text,
                fontSize = 18.sp,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun FullName(firstname: String, lastname: String, modifier: Modifier = Modifier) {
    Text(
        text = "$firstname\n$lastname", fontSize = 18.sp, fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}

@Composable
fun ImageProfile(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_profile),
        contentDescription = "profile image",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun Status(text: String = "", modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier

    )
}

@Composable
fun TopBarProfile() {
    TopAppBar(
        title = { Text(text = "Профиль") },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}
