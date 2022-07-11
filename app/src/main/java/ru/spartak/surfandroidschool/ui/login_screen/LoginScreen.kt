package ru.spartak.surfandroidschool.ui.login_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ru.spartak.surfandroidschool.R
import ru.spartak.surfandroidschool.ui.theme.DefaultTheme
import ru.spartak.surfandroidschool.ui.theme.spacing


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen() {
    DefaultTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.entry),
                            style = MaterialTheme.typography.h1
                        )
                    },
                    modifier = Modifier.height(56.dp),
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent
                )
            },
        ) {
            val spacing = MaterialTheme.spacing
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (loginView, passwordView, signInBtn) = createRefs()
                LoginView(modifier = Modifier.constrainAs(loginView) {
                    top.linkTo(parent.top, margin = spacing.large)
                    start.linkTo(parent.start, margin = spacing.medium)
                    end.linkTo(parent.end, margin = spacing.medium)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(55.dp)
                })
                PasswordView(modifier = Modifier.constrainAs(passwordView) {
                    top.linkTo(loginView.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = spacing.medium)
                    end.linkTo(parent.end, margin = spacing.medium)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(55.dp)
                })
                SignInBtn(modifier = Modifier.constrainAs(signInBtn) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = spacing.medium)
                    end.linkTo(parent.end, margin = spacing.medium)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(48.dp)
                })
            }
        }
    }
}

@Composable
fun LoginView(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val maxChar = 10
    val mobileNumberTransformer = MobileNumberTransformer()
    TextField(
        value = text,
        onValueChange = {
            if (it.length <= maxChar) text = it
        },
        label = {
            Text(text = stringResource(id = R.string.login), color = MaterialTheme.colors.onSurface)
        },
        visualTransformation = { mobileNumberTransformer.transformNumber(it) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.high),
        ),
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@Composable
fun PasswordView(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        value = text, onValueChange = { text = it }, singleLine = true,
        label = {
            Text(
                text = stringResource(R.string.password),
                color = MaterialTheme.colors.onSurface
            )
        },
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),

        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility_24)
            else painterResource(id = R.drawable.ic_visibility_off_24)
            val description =
                if (passwordVisible) stringResource(R.string.hidePassword) else stringResource(
                    R.string.showPassword
                )
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, description)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.high),
        ),
    )
}

@Composable
fun SignInBtn(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = RectangleShape
    ) {
        Text(text = stringResource(R.string.signIn))
    }
}