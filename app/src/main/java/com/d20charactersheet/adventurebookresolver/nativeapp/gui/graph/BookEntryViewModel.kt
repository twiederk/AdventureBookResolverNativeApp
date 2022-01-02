package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark

class BookEntryViewModel(private val bookEntry: BookEntry) : ViewModel() {

    val id = bookEntry.id
    val title = bookEntry.title
    val note = bookEntry.note

    var wayMark by mutableStateOf(bookEntry.wayMark)
        private set

    fun onWayMarkSelected(wayMark: WayMark) {
        bookEntry.wayMark = wayMark
        this.wayMark = wayMark
    }

}
