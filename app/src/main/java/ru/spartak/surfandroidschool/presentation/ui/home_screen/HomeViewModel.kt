package ru.spartak.surfandroidschool.presentation.ui.home_screen

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.domain.repository.PictureRepository
import ru.spartak.surfandroidschool.utils.checkForInternet
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private var _pictureDataList = MutableStateFlow<List<PictureData>>(mutableListOf())
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
                if (it.title.lowercase().contains(searchText.lowercase())) emit(it)
            }
        }
    }

    fun fetchPicture() {
        viewModelScope.launch(Dispatchers.IO) {
            _pictureDataList.value =
                if (checkForInternet()) pictureRepository.syncFetchPicture()
                else pictureRepository.fetchPicture()

        }
    }

    fun checkForInternet():Boolean{
        return checkForInternet(context)
    }

    fun updatePicture(pictureData: PictureData) {
        viewModelScope.launch(Dispatchers.IO) {
            pictureRepository.updatePicture(pictureData)
            fetchPicture()
        }
    }

}

