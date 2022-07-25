package ru.spartak.surfandroidschool.presentation.ui.main_activity

import androidx.compose.runtime.mutableStateOf
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.utils.Constants
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel@Inject constructor(private val sharedPreferenceHelper: UserSharedPreferenceHelper):ViewModel() {
    fun isLogin():Boolean{
        val token = sharedPreferenceHelper.loadData(Constants.USER_TOKEN)
        return token != null && token.isNotEmpty()
    }


}