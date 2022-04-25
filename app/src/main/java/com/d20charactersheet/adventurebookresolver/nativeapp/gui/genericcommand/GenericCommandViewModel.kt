package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class GenericCommandViewModel : ViewModel() {

    private val _argument = MutableLiveData("")
    val argument: LiveData<String> = _argument

    var bookEntryList by mutableStateOf<List<BookEntry>>(emptyList())
        private set

    var actionList by mutableStateOf<List<Action>>(emptyList())
        private set

    var remainingCombinations by mutableStateOf(0L)
        private set

    var maxCombinations by mutableStateOf(0L)
        private set

    var solutionList by mutableStateOf<List<Solution>>(emptyList())
        private set

    var outputText by mutableStateOf("")
        private set

    var numberOfSolutions by mutableStateOf(0)
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

    fun onMaxCombinationsChange(maxCombinations: Long) {
        this.maxCombinations = maxCombinations
    }

    fun onRemainingCombinationsChange(numberOfCombinations: Long) {
        this.remainingCombinations = maxCombinations - numberOfCombinations
    }

    fun execute(command: Command, argument: String, game: Game): String = CommandExecutor().execute(command, argument, game, viewModelScope)

    fun onSolutionListChange(solutionList: List<Solution>) {
        this.solutionList = solutionList
    }

    fun onOutputTextChange(outputText: String) {
        this.outputText = outputText
    }

    fun onSolutionFoundChange(numberOfSolutions: Int) {
        this.numberOfSolutions = numberOfSolutions
    }

}