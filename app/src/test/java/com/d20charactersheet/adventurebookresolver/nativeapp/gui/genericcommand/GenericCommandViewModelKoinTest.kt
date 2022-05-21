package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class GenericCommandViewModelKoinTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
    }

    @After
    fun after() {
        stopKoin()
    }


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val underTest = GenericCommandViewModel()

    @Test
    fun onArgumentChange() {

        // act
        underTest.onArgumentChange("myArgument")

        // assert
        assertThat(underTest.argument).isEqualTo("myArgument")
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
        val solutions = listOf(Solution(listOf()))

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

    @Test
    fun onSolutionFoundChange() {

        // act
        underTest.onSolutionFoundChange(1)

        // assert
        assertThat(underTest.numberOfSolutions).isEqualTo(1)

    }

    @Test
    fun onCommandChange() {

        // act
        underTest.onCommandChange(Command.Search)

        // assert
        assertThat(underTest.command).isEqualTo(Command.Search)
    }

    @Test
    fun onPathClick() {

        // arrange
        underTest.onBookEntryListChange(listOf(BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure")))
        underTest.onActionListChange(listOf(Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200))))
        underTest.onMaxCombinationsChange(1)
        underTest.onSolutionListChange(listOf(Solution(listOf())))
        underTest.onOutputTextChange("myOutputText")
        whenever(game.displayPath()).thenReturn(listOf(BookEntry(2)))

        // act
        underTest.onPathClick()

        // assert
        assertThat(underTest.bookEntryList).containsExactly(BookEntry(2))
        assertThat(underTest.outputText).isEmpty()
        assertThat(underTest.actionList).isEmpty()
        assertThat(underTest.maxCombinations).isZero
        assertThat(underTest.solutionList).isEmpty()
    }

    @Test
    fun onWayPointClick() {
        // arrange
        underTest.onBookEntryListChange(listOf(BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure")))
        underTest.onActionListChange(listOf(Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200))))
        underTest.onMaxCombinationsChange(1)
        underTest.onSolutionListChange(listOf(Solution(listOf())))
        underTest.onOutputTextChange("myOutputText")
        whenever(game.displayWayPoints()).thenReturn(listOf(BookEntry(2)))

        // act
        underTest.onWayPointClick()

        // assert
        assertThat(underTest.bookEntryList).containsExactly(BookEntry(2))
        assertThat(underTest.actionList).isEmpty()
        assertThat(underTest.maxCombinations).isZero
        assertThat(underTest.solutionList).isEmpty()
        assertThat(underTest.outputText).isEmpty()
    }

    @Test
    fun onUnvisitedClick() {
        // arrange
        underTest.onBookEntryListChange(listOf(BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure")))
        underTest.onActionListChange(listOf(Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200))))
        underTest.onMaxCombinationsChange(1)
        underTest.onSolutionListChange(listOf(Solution(listOf())))
        underTest.onOutputTextChange("myOutputText")
        val action = Action("myLabel", BookEntry(1), BookEntry(2))
        whenever(game.displayActionsToUnvisitedEntries()).thenReturn(listOf(action))

        // act
        underTest.onUnvisitedClick()

        // assert
        assertThat(underTest.actionList).containsExactly(action)
        assertThat(underTest.bookEntryList).isEmpty()
        assertThat(underTest.maxCombinations).isZero
        assertThat(underTest.solutionList).isEmpty()
        assertThat(underTest.outputText).isEmpty()
    }

    @Test
    fun clear() {
        // arrange
        underTest.onBookEntryListChange(listOf(BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure")))
        underTest.onActionListChange(listOf(Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200))))
        underTest.onMaxCombinationsChange(1)
        underTest.onSolutionListChange(listOf(Solution(listOf())))
        underTest.onOutputTextChange("myOutputText")

        // act
        underTest.clear()

        // assert
        assertThat(underTest.actionList).isEmpty()
        assertThat(underTest.bookEntryList).isEmpty()
        assertThat(underTest.maxCombinations).isZero
        assertThat(underTest.solutionList).isEmpty()
        assertThat(underTest.outputText).isEmpty()
    }

}