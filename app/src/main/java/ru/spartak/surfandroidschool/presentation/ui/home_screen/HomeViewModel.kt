package ru.spartak.surfandroidschool.presentation.ui.home_screen

import android.app.PictureInPictureParams
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.model.NetworkResult
import ru.spartak.surfandroidschool.domain.model.PictureData
import ru.spartak.surfandroidschool.domain.repository.PictureRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
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
                if (it.title.contains(searchText)) emit(it)
            }
        }
    }

    fun fetchPicture() {
        viewModelScope.launch(Dispatchers.IO) {
            val pictureList = pictureRepository.fetchPicture()
            if (pictureList.isEmpty()) {
                pictureRepository.getPictureFromNetwork().collect { networkResult ->
                    when(networkResult){
                        is NetworkResult.Success -> {
                            _pictureDataList.value = networkResult.data!!
                            _pictureDataList.value.forEach {
                                pictureRepository.addPicture(it)
                            }
                        }
                        is NetworkResult.Error -> Log.d("AAA", "fetchPicture: Error\n${networkResult.message}")
                        is NetworkResult.Throw -> Log.d("AAA", "fetchPicture: Throw\n${networkResult.message}")
                        else -> {}
                    }
                }
            }
            else _pictureDataList.value = pictureList

        }
    }

    fun updatePicture(pictureData: PictureData) {
        Log.d("AAA", "updatePicture: ТУТ $pictureData")
        viewModelScope.launch(Dispatchers.IO) {
            pictureRepository.updatePicture(pictureData)
        }
    }

}