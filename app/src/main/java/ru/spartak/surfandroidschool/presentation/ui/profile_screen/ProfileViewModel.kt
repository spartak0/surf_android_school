package ru.spartak.surfandroidschool.presentation.ui.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.domain.model.UserData
import ru.spartak.surfandroidschool.domain.repository.UserRepository
import ru.spartak.surfandroidschool.utils.Constants
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferenceHelper: UserSharedPreferenceHelper
) : ViewModel() {
    var _user = MutableStateFlow(UserData())
    val user = _user.asStateFlow()

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferenceHelper.loadData(Constants.CURRENT_USER_ID)
                ?.let { userRepository.getUser(it) }
        }

    }
}