package ru.spartak.surfandroidschool.presentation.ui.profile_screen

import android.util.Log
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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferenceHelper: UserSharedPreferenceHelper
) : ViewModel() {
    private var _user = MutableStateFlow(UserData())
    val user = _user.asStateFlow()

    private var _logoutState = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Nothing())
    val logoutState = _logoutState.asStateFlow()

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getCurrentUser()?.let {
                _user.value = it
            }
        }

    }

    fun logout() {
        Log.d("AAA", "logout: В логауте")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logout().collect { state ->
                run {
                    Log.d("AAA", "logout: ПРИШЛО ИЗ ФЛОУ $state")
                    _logoutState.value = state
                }
            }
        }

    }
}