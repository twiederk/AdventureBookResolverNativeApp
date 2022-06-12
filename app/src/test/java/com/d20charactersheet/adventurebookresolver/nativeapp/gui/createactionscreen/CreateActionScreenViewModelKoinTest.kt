package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreateActionScreenViewModelKoinTest : KoinTest {

    private val underTest: CreateActionScreenViewModel by inject()
    private val game: Game by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

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

    @Test
    fun `should init with default values`() {
        // assert
        assertThat(underTest.actionLabel).isEmpty()
        assertThat(underTest.entryId).isEmpty()
    }

    @Test
    fun `should reset view model with default values`() {
        // arrange
        underTest.onActionLabelChange("myAction")
        underTest.onEntryIdChange(",,")
        underTest.onCreateClick()

        // act
        underTest.reset()

        // assert
        assertThat(underTest.actionLabel).isEmpty()
        assertThat(underTest.entryId).isEmpty()
        assertThat(underTest.errorMessage).isEmpty()
    }

    @Test
    fun `should update action when text entered`() {

        // act
        underTest.onActionLabelChange("myAction")

        // assert
        assertThat(underTest.actionLabel).isEqualTo("myAction")
    }

    @Test
    fun `should update id when text entered`() {

        // act
        underTest.onEntryIdChange("100")

        // assert
        assertThat(underTest.entryId).isEqualTo("100")
    }

    @Test
    fun `should create action when action label and entry id are valid`() {
        // arrange
        underTest.onActionLabelChange("myAction")
        underTest.onEntryIdChange("10")
        whenever(game.book).thenReturn(AdventureBook())

        // act
        val result = underTest.onCreateClick()

        // assert
        assertThat(result).isTrue
        verify(game).addAction("myAction", 10)
    }

    @Test
    fun `should display error message when action label is empty`() {
        // arrange
        underTest.onActionLabelChange("")
        underTest.onEntryIdChange("10")

        // act
        val result = underTest.onCreateClick()

        // assert
        assertThat(result).isFalse
        assertThat(underTest.errorMessage).isEqualTo("Can't create action: Label is missing")
    }

    @Test
    fun `should display error message when entry id is empty`() {
        // arrange
        underTest.onActionLabelChange("myAction")
        underTest.onEntryIdChange("")

        // act
        val result = underTest.onCreateClick()

        // assert
        assertThat(result).isFalse
        assertThat(underTest.errorMessage).isEqualTo("Can't create action: Id is empty")
    }

    @Test
    fun `should display error message when entry id cannot be parsed`() {
        // arrange
        underTest.onActionLabelChange("myAction")
        underTest.onEntryIdChange(",,")

        // act
        val result = underTest.onCreateClick()

        // assert
        assertThat(result).isFalse
        assertThat(underTest.errorMessage).isEqualTo("Can't create action: Id is invalid")
    }

    @Test
    fun `should display error message when entry id is same as entry id of current entry`() {
        // arrange
        underTest.onActionLabelChange("myAction")
        underTest.onEntryIdChange("1")
        whenever(game.book).thenReturn(AdventureBook())

        // act
        val result = underTest.onCreateClick()

        // assert
        assertThat(result).isFalse
        assertThat(underTest.errorMessage).isEqualTo("Can't create action: Id is same as current node")
    }

}