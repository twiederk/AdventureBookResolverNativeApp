package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ItemAdapterKoinTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onBindViewHolder() {
        // Arrange
        val itemViewHolder: ItemViewHolder = mock()
        game.addItemToInventory("myItem")

        // Act
        ItemAdapter()
            .onBindViewHolder(itemViewHolder, 0)

        // Assert
        verify(itemViewHolder).setItem(Item("myItem"))
    }

    @Test
    fun getItemCount() {
        // Arrange
        game.addItemToInventory("item 1")
        game.addItemToInventory("item 2")

        // Act
        val itemCount = ItemAdapter()
            .itemCount

        // Assert
        assertThat(itemCount).isEqualTo(2)
    }

    @Test
    fun deleteItem() {
        // Arrange
        declareMock<Game>()

        // Act
        ItemAdapter().deleteItem(0)

        // Assert
        verify(game).removeItemFromInventory(0)
    }

}



