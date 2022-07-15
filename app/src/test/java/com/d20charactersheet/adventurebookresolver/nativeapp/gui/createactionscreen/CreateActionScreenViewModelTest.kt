package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreateActionScreenViewModelTest {

    private val game: Game = mock()
    private val underTest = CreateActionScreenViewModel(game)

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
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
        assertThat(underTest.actionLabel).isEmpty()
        assertThat(underTest.entryId).isEmpty()
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