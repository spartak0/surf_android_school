package ru.spartak.surfandroidschool.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.spartak.surfandroidschool.domain.model.Picture


class HomeViewModel : ViewModel() {
    private var _pictureList = MutableStateFlow(mutableListOf<Picture>())
    val pictureList: StateFlow<List<Picture>> = _pictureList.asStateFlow()

    fun newSearch(searchText: String): List<Picture> {
        val list = mutableListOf<Picture>()
        viewModelScope.launch {
            searchItems(searchText).collectLatest {
                list.add(it)
            }
        }
        return list
    }

    private fun searchItems(searchText: String): Flow<Picture> {
        return flow {
            _pictureList.value.forEach {
                if (it.title.contains(searchText)) emit(it)
            }
        }
    }

    fun addPicture() {
        _pictureList.value = mutableListOf<Picture>(
            Picture("0", "1", "aboba", "123", "12.03.2002"),
            Picture("0", "123", "aboba", "123", "12.03.2002"),
            Picture("0", "123", "aboba", "123", "12.03.2002"),
            Picture("0", "122", "aboba", "123", "12.03.2002"),
            Picture("0", "dsad", "aboba", "123", "12.03.2002"),
            Picture("0", "asdasd", "aboba", "123", "12.03.2002"),
            Picture("0", "dsadsa", "aboba", "123", "12.03.2002"),
            Picture("0", "dsadas", "aboba", "123", "12.03.2002"),
            Picture("0", "cdss", "aboba", "123", "12.03.2002"),
        )
    }

    fun updatePicture(){
        //todo update picture in database
    }

}