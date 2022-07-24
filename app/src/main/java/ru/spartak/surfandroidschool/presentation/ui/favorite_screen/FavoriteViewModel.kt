package ru.spartak.surfandroidschool.presentation.ui.favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.domain.repository.PictureRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val pictureRepository: PictureRepository) :
    ViewModel() {

    private var _favoritePictureList = MutableStateFlow<List<PictureData>>(mutableListOf())
    val favoritePictureList = _favoritePictureList.asStateFlow()

    fun fetchFavoritePicture() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritePictureList.value = pictureRepository.fetchFavoritePicture()
        }
    }

    fun updatePicture(newPictureValue: PictureData) {
        viewModelScope.launch(Dispatchers.IO) {
            pictureRepository.updatePicture(newPictureValue)
            fetchFavoritePicture()
        }

    }
}