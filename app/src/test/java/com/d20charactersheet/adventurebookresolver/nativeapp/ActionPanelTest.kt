package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class ActionPanelTest {




    @Test
    fun `ActionMoveAdapter get item count`() {
        // Arrange
        val game = Game()
        game.book.addAction("myFirstAction", 10)
        game.book.addAction("mySecondAction", 20)

        // Act
        val itemCount = ActionMoveAdapter(game).itemCount

        // Assert
        assertThat(itemCount).isEqualTo(2)
    }

    @Test
    fun `ActionMoveAdapter bind view holder`() {
        // Arrange
        val game = Game()
        game.book.addAction("myFirstAction", 10)
        game.book.addAction("mySecondAction", 20)

        val actionMoveViewHolder: ActionMoveViewHolder = mock()

        // Act
        ActionMoveAdapter(game).onBindViewHolder(actionMoveViewHolder, 0)

        // Assert
        verify(actionMoveViewHolder).setAction("myFirstAction", 10)
    }


}

