package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class EntryScreenViewModel(private val game: Game) : ViewModel() {

    private var bookEntry: BookEntry = BookEntry(id = 0)

    var id = 0
    var title by mutableStateOf("")
    var note by mutableStateOf("")
    var visit by mutableStateOf(bookEntry.visit)
    var wayMark by mutableStateOf(bookEntry.wayMark)
    var actions by mutableStateOf(emptyList<Action>())

    fun initBookEntry(bookEntry: BookEntry) {
        this.bookEntry = bookEntry
        id = bookEntry.id
        title = bookEntry.title
        note = bookEntry.note
        visit = bookEntry.visit
        wayMark = bookEntry.wayMark
        actions = this.game.getActions(bookEntry)
    }

    fun onTitleChanged(title: String) {
        bookEntry.title = title
        this.title = title
    }

    fun onNoteChanged(note: String) {
        bookEntry.note = note
        this.note = note
    }

    fun onWayMarkSelected(wayMark: WayMark) {
        bookEntry.wayMark = wayMark
        this.wayMark = wayMark
    }

    fun onActionMoveClicked(entryId: Int) {
        this.game.move(entryId)
    }

    fun onActionDeleteClicked(entryId: Int) {
        this.game.delete(entryId)
        actions = this.game.getActions(bookEntry)
    }

    fun onRunClick() {
        this.game.runTo(bookEntry.id)
    }

}
