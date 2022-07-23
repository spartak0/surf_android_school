package ru.spartak.surfandroidschool.presentation.ui.login_screen

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.UserData
import ru.spartak.surfandroidschool.domain.repository.UserRepository
import ru.spartak.surfandroidschool.presentation.ui.Error
import ru.spartak.surfandroidschool.utils.Constants
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _loginState = MutableStateFlow<NetworkResult<UserData>>(NetworkResult.Nothing())
    val loginState = _loginState.asStateFlow()

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

    fun login(phone: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.login("+7$phone", password).collect { state ->
                run {
                    Log.d("AAA", "login: EЩЁ ПРИШЛО")
                    _loginState.value = state
                }
            }
        }
    }

    fun addUserInDatabase(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(user)
        }
    }
}

