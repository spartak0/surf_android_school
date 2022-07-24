package ru.spartak.surfandroidschool.presentation.ui.main_activity

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.utils.Constants
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel@Inject constructor(val sharedPreferenceHelper: UserSharedPreferenceHelper):ViewModel() {
    fun isLogin():Boolean{
        val token = sharedPreferenceHelper.loadData(Constants.USER_TOKEN)
        return token != null && token.isNotEmpty()
    }

}