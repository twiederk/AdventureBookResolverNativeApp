package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BookEntryViewModelTest {

    @Test
    fun `should init book entry view model with given book entry`() {
        // arrange
        val bookEntry = BookEntry(
            id = 1,
            title = "myTitle",
            note = "myNote",
            wayMark = WayMark.DEAD_END
        )

        // act
        val bookEntryViewModel = BookEntryViewModel(bookEntry)

        // assert
        assertThat(bookEntryViewModel.id).isEqualTo(1)
        assertThat(bookEntryViewModel.title).isEqualTo("myTitle")
        assertThat(bookEntryViewModel.note).isEqualTo("myNote")
        assertThat(bookEntryViewModel.wayMark).isEqualTo(WayMark.DEAD_END)
    }

    @Test
    fun `should update way mark of book entry`() {
        // arrange
        val bookEntry = BookEntry(1)

        // act
        BookEntryViewModel(bookEntry).onWayMarkSelected(WayMark.WAY_POINT)

        // assert
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.WAY_POINT)
    }

}