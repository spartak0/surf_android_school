package ru.spartak.surfandroidschool.presentation.ui.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.utils.Constants
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userSharedPreferenceHelper: UserSharedPreferenceHelper
) :
    ViewModel() {
    private var _pictureDataList = MutableStateFlow(mutableListOf<PictureData>())
    val pictureDataList: StateFlow<List<PictureData>> = _pictureDataList.asStateFlow()

    fun newSearch(searchText: String): List<PictureData> {
        val list = mutableListOf<PictureData>()
        viewModelScope.launch {
            searchItems(searchText).collectLatest {
                list.add(it)
            }
        }
        return list
    }

    private fun searchItems(searchText: String): Flow<PictureData> {
        return flow {
            _pictureDataList.value.forEach {
                if (it.title.contains(searchText)) emit(it)
            }
        }
    }

    fun addPicture() {
        _pictureDataList.value = mutableListOf<PictureData>(
        )
    }

    fun getToken():String? {
        return userSharedPreferenceHelper.loadData(Constants.USER_TOKEN)
    }

    fun updatePicture() {
        //todo update picture in database
    }

}