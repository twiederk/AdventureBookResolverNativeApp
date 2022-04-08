package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Combination
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
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

    @Test
    fun onActionListChange() {
        // arrange
        val actionList = listOf(Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200)))

        // act
        underTest.onActionListChange(actionList)

        // assert
        assertThat(underTest.actionList).isEqualTo(actionList)
    }

    @Test
    fun onMaxCombinationsChange() {

        // act
        underTest.onMaxCombinationsChange(200)

        // assert
        assertThat(underTest.maxCombinations).isEqualTo(200)
    }

    @Test
    fun onRemainingCombinationsChange() {
        // arrange
        underTest.onMaxCombinationsChange(200)

        // act
        underTest.onRemainingCombinationsChange(50)

        // assert
        assertThat(underTest.remainingCombinations).isEqualTo(150)
    }

    @Test
    fun onSolutionListChange() {

        // arrange
        val solutions = listOf(Solution(Combination(arrayOf())))

        // act
        underTest.onSolutionListChange(solutions)

        // assert
        assertThat(underTest.solutionList).isEqualTo(solutions)
    }

    @Test
    fun onOutputTextChange() {

        // act
        underTest.onOutputTextChange("myOutputText")

        // assert
        assertThat(underTest.outputText).isEqualTo("myOutputText")
    }

}