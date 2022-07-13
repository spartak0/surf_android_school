package ru.spartak.surfandroidschool.ui.login_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.ui.theme.spacing


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = viewModel()) {
    var textLogin by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var verificationErrorLogin by remember { mutableStateOf("") }
    var verificationErrorPassword by remember { mutableStateOf("") }
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
                val (loginView, passwordView, signInBtn) = createRefs()
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
                    error = verificationErrorLogin
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
                    error = verificationErrorPassword
                )
                SignInBtn(modifier = Modifier.constrainAs(signInBtn) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = spacing.medium)
                    end.linkTo(parent.end, margin = spacing.medium)
                    width = Dimension.fillToConstraints
                }) {
                    //todo спросить по поводу полной инкапсуляцции во вм
                    val validationLogin = viewModel.validationTestLogin(textLogin)
                    val validationPassword = viewModel.validationTestPassword(textPassword)
                    verificationErrorLogin = if (!validationLogin.first)
                        validationLogin.second
                    else ""
                    verificationErrorPassword = if (!validationPassword.first)
                        validationPassword.second
                    else ""
                }
            }
        }
    }
}

@Composable
fun TopBar(){
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
    error: String = ""
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
            isError = error.isNotEmpty()
        )
        if (error.isNotEmpty()) {
            ValidationErrorText(text = error)
        }
    }
}

@Composable
fun PasswordView(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String = ""
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
            isError = error.isNotEmpty()
        )
        if (error.isNotEmpty()) {
            ValidationErrorText(text = error)
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

@Composable
fun SignInBtn(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RectangleShape
    ) {
        Text(text = stringResource(R.string.signIn))
    }
}