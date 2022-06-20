package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateActionScreenViewModel : KoinComponent, ViewModel() {

    private val game: Game by inject()

    var actionLabel by mutableStateOf("")
        private set

    var entryId by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun onActionLabelChange(actionLabel: String) {
        this.actionLabel = actionLabel
    }

    fun onEntryIdChange(entryId: String) {
        this.entryId = entryId
    }

    fun onCreateClick(): Boolean {
        if (!isActionLabelValid() || !isEntryIdValid()) {
            errorMessage = createErrorMessage()
            return false
        }
        game.addAction(actionLabel, entryId.toInt())
        actionLabel = ""
        entryId = ""
        return true
    }

    private fun createErrorMessage(): String {
        if (!isActionLabelValid()) {
            return "Can't create action: Label is missing"
        }
        if (entryId.isEmpty()) {
            return "Can't create action: Id is empty"
        }
        if (!isEntryIdParsable()) {
            return "Can't create action: Id is invalid"
        }
        if (entryId.toInt() == game.book.getEntryId()) {
            return "Can't create action: Id is same as current node"
        }
        return "Unknown error"
    }

    private fun isEntryIdParsable(): Boolean = try {
        entryId.toInt()
        true
    } catch (numberFormatException: java.lang.NumberFormatException) {
        false
    }

    private fun isActionLabelValid() = actionLabel.isNotEmpty()

    private fun isEntryIdValid(): Boolean = try {
        val entryId = entryId.toInt()
        entryId != game.book.getEntryId()
    } catch (numberFormatException: NumberFormatException) {
        false
    }

    fun reset() {
        this.actionLabel = ""
        this.entryId = ""
        this.errorMessage = ""
    }

}
