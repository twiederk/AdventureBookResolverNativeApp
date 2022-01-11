package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GenericCommandViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val underTest = GenericCommandViewModel()

    @Test
    fun onArgumentChange() {

        // act
        underTest.onArgumentChange("myArgument")

        // assert
        assertThat(underTest.argument.value).isEqualTo("myArgument")
    }

    @Test
    fun onBookEntryListChange() {
        // arrange
        val bookEntryList = listOf(BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure"))

        // act
        underTest.onBookEntryListChange(bookEntryList)

        // assert
        assertThat(underTest.bookEntryList).isEqualTo(bookEntryList)
    }

}