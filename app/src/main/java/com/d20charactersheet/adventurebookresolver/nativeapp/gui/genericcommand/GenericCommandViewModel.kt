package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry

class GenericCommandViewModel : ViewModel() {

    private val _argument = MutableLiveData("")
    val argument: LiveData<String> = _argument

    var searchResult by mutableStateOf<List<BookEntry>>(emptyList())
        private set

    fun onArgumentChange(argument: String) {
        _argument.value = argument
    }

    fun onSearchResultChange(searchResult: List<BookEntry>) {
        this.searchResult = searchResult
    }

}