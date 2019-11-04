package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class InventoryAdapterKoinTest : KoinTest {

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
        val inventoryViewHolder: InventoryViewHolder = mock()
        game.addItemToInventory("myItem")

        // Act
        InventoryAdapter().onBindViewHolder(inventoryViewHolder, 0)

        // Assert
        verify(inventoryViewHolder).setItem(Item("myItem"))
    }

    @Test
    fun getItemCount() {
        // Arrange
        game.addItemToInventory("item 1")
        game.addItemToInventory("item 2")

        // Act
        val itemCount = InventoryAdapter().itemCount

        // Assert
        assertThat(itemCount).isEqualTo(2)
    }

    @Test
    fun deleteItem() {
        // Arrange
        declareMock<Game>()

        // Act
        InventoryAdapter().deleteItem(0)

        // Assert
        verify(game).removeItemFromInventory(0)
    }

}



