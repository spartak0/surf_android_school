package ru.spartak.surfandroidschool.ui.login_screen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel

class LoginScreenViewModel: ViewModel() {
    fun validationTestLogin(login: String):Pair<Boolean,String> {
        if (login.isEmpty()) return Pair(false, "Поле не может быть пустым")
        if (login.length!=10 || !login.isDigitsOnly()) return Pair(false, "Не верный формат номера телефона")
        return Pair(true,"")
    }
    fun validationTestPassword(password: String):Pair<Boolean,String> {
        if (password.isEmpty()) return Pair(false, "Поле не может быть пустым")
        if (password.length<6 || password.length>255) return Pair(false, "Не подходящий размер пароля")
        return Pair(true,"")
    }
}

