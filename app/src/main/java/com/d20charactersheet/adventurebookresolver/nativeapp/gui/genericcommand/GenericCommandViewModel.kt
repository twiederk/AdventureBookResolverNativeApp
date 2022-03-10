package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry

class GenericCommandViewModel : ViewModel() {

    private val _argument = MutableLiveData("")
    val argument: LiveData<String> = _argument

    var bookEntryList by mutableStateOf<List<BookEntry>>(emptyList())
        private set

    var actionList by mutableStateOf<List<Action>>(emptyList())
        private set

    fun onArgumentChange(argument: String) {
        _argument.value = argument
    }

    fun onBookEntryListChange(bookEntryList: List<BookEntry>) {
        this.bookEntryList = bookEntryList
    }

    fun onActionListChange(actionList: List<Action>) {
        this.actionList = actionList
    }

}