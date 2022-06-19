package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SolutionScreenViewModel : KoinComponent, ViewModel() {

    private val game: Game by inject()

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

    fun onSearchClick() {
        clear()
        onBookEntryListChange(game.search(argument))
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

    fun clear() {
        bookEntryList = emptyList()
        actionList = emptyList()
        maxCombinations = 0
        solutionList = emptyList()
        outputText = ""
    }

    fun onPathClick() {
        clear()
        onBookEntryListChange(game.displayPath())
    }

    fun onWayPointClick() {
        clear()
        onBookEntryListChange(game.displayWayPoints())
    }

    fun onUnvisitedClick() {
        clear()
        onActionListChange(game.displayActionsToUnvisitedEntries())
    }

    fun onSolveClick() {
        clear()
        val solutionScreenViewModel = this
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onSolutionListChange(game.solve(ComposeBookSolverListener(solutionScreenViewModel)))
            } catch (throwable: Throwable) {
                onOutputTextChange(throwable.localizedMessage ?: "Exception without message")
            }
        }
    }

    fun onDieRollClick(dieRoll: String) {
        clear()
        onOutputTextChange(game.rollDie(dieRoll))
    }


}