package ru.spartak.surfandroidschool.ui.login_screen

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.data.entity.LoginRequest
import ru.spartak.surfandroidschool.data.network.RetrofitApi
import ru.spartak.surfandroidschool.data.network.RetrofitHelper
import ru.spartak.surfandroidschool.ui.Error

class LoginScreenViewModel : ViewModel() {
    fun validationTestLogin(login: String): Error {
        if (login.isEmpty()) return Error(true, "Поле не может быть пустым")
        if (login.length != 10 || !login.isDigitsOnly()) return Error(
            true,
            "Не верный формат номера телефона"
        )
        return Error(false, "")
    }

    fun validationTestPassword(password: String): Error {
        if (password.isEmpty()) return Error(true, "Поле не может быть пустым")
        if (password.length < 6 || password.length > 255) return Error(
            true,
            "Не подходящий размер пароля"
        )
        return Error(false, "")
    }

    fun login(phone: String, password: String, success: () -> Unit, notSuccess: ()->Unit) {
        val api = RetrofitHelper.getInstance().create(RetrofitApi::class.java)
        viewModelScope.launch {
            val result = api.login(loginRequest = LoginRequest("+7$phone", password))
            if (result.isSuccessful) success()
            else notSuccess()
        }
    }
}

