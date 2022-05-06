package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenericCommandViewModel : KoinComponent, ViewModel() {

    private val game: Game by inject()

    var command by mutableStateOf(Command.Create)
        private set

    var argument by mutableStateOf("")
        private set

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
        this.argument = argument
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

    fun execute() {
        onOutputTextChange(CommandExecutor().execute(command, argument, game, viewModelScope))
    }

    fun onSolutionListChange(solutionList: List<Solution>) {
        this.solutionList = solutionList
    }

    fun onOutputTextChange(outputText: String) {
        this.outputText = outputText
    }

    fun onSolutionFoundChange(numberOfSolutions: Int) {
        this.numberOfSolutions = numberOfSolutions
    }

    fun onCommandChange(command: Command) {
        this.command = command
    }

    fun onClearClick() {
        bookEntryList = emptyList()
        actionList = emptyList()
        maxCombinations = 0
        solutionList = emptyList()
        outputText = ""
    }

}