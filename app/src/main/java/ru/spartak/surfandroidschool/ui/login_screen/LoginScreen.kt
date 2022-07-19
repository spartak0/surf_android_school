package ru.spartak.surfandroidschool.ui.login_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.ui.Error
import ru.spartak.surfandroidschool.ui.detail.BottomBtn
import ru.spartak.surfandroidschool.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.ui.theme.spacing


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = viewModel()) {
    var textLogin by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }

    var verificationLogin by remember { mutableStateOf(Error()) }
    var verificationPassword by remember { mutableStateOf(Error()) }

    val isBottomError = remember { mutableStateOf(false) }
    DefaultTheme {
        Scaffold(
            topBar = {
                TopBar()
            },
        ) {
            val spacing = MaterialTheme.spacing
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (loginView, passwordView, signInBtn, bottomViewError) = createRefs()
                val maxCharLogin = 10
                LoginView(
                    text = textLogin,
                    onValueChange = {
                        if (it.length <= maxCharLogin) textLogin = it
                    },
                    modifier = Modifier.constrainAs(loginView) {
                        top.linkTo(parent.top, margin = spacing.large)
                        start.linkTo(parent.start, margin = spacing.medium)
                        end.linkTo(parent.end, margin = spacing.medium)
                        width = Dimension.fillToConstraints
                    },
                    error = verificationLogin
                )

                PasswordView(
                    text = textPassword,
                    onValueChange = { textPassword = it },
                    modifier = Modifier.constrainAs(passwordView) {
                        top.linkTo(loginView.bottom, margin = 12.dp)
                        start.linkTo(parent.start, margin = spacing.medium)
                        end.linkTo(parent.end, margin = spacing.medium)
                        width = Dimension.fillToConstraints
                    },
                    error = verificationPassword
                )

                if (isBottomError.value) {
                    BottomErrorSnackbar(modifier = Modifier.constrainAs(bottomViewError) {
                        bottom.linkTo(signInBtn.top, spacing.small)
                        start.linkTo(parent.start, spacing.small)
                        end.linkTo(parent.end, spacing.small)
                        height = Dimension.value(48.dp)
                        width = Dimension.fillToConstraints
                    }) { isBottomError.value = false }
                }


                BottomBtn(
                    text = stringResource(id = R.string.signIn),
                    modifier = Modifier
                        .constrainAs(signInBtn) {
                            bottom.linkTo(parent.bottom, margin = 20.dp)
                            start.linkTo(parent.start, margin = spacing.medium)
                            end.linkTo(parent.end, margin = spacing.medium)
                            width = Dimension.fillToConstraints
                            height = Dimension.value(48.dp)
                        }) {
                    verificationLogin = viewModel.validationTestLogin(textLogin)
                    verificationPassword = viewModel.validationTestPassword(textPassword)
                    if (!verificationLogin.isError && !verificationPassword.isError) viewModel.login(
                        phone = textLogin,
                        password = textPassword,
                        success = {
                            //todo navigate
                        },
                        notSuccess = {
                            isBottomError.value = true
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomErrorSnackbar(modifier: Modifier, snackbarOnClick: () -> Unit) {
    Card(
        modifier = modifier,
        onClick = { snackbarOnClick() },
        elevation = 0.dp,
        shape = RectangleShape) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.error),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Неправильный логин или пароль",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500),
                color = MaterialTheme.colors.onError
            )
        }
    }
}


@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.entry)) },
        modifier = Modifier.height(56.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun LoginView(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: Error
) {
    val mobileNumberTransformer = MobileNumberTransformer()
    Column(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onSurface
                )
            },
            visualTransformation = { mobileNumberTransformer.transformNumber(it) },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.high),
            ),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = error.isError
        )
        if (error.isError) {
            ValidationErrorText(text = error.message)
        }
    }
}

@Composable
fun PasswordView(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: Error,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.password),
                    color = MaterialTheme.colors.onSurface
                )
            },
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),

            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (text.isNotEmpty()) {
                    val image =
                        if (passwordVisible) painterResource(id = R.drawable.ic_visibility_24)
                        else painterResource(id = R.drawable.ic_visibility_off_24)
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, description)
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.high),
            ),
            isError = error.isError
        )
        if (error.isError) {
            ValidationErrorText(text = error.message)
        }
    }
}


@Composable
fun ValidationErrorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(start = MaterialTheme.spacing.medium, top = 0.dp)
    )
}
