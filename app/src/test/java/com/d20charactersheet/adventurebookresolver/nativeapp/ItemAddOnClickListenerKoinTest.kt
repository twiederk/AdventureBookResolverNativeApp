package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock


class ItemAddOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val inventoryPanel: InventoryPanel by inject()

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
    fun onClick_adds_item() {
        // Arrange
        declareMock<Game>()
        declareMock<InventoryPanel> {
            given(getItem()).willReturn("myItem")
        }

        // Act
        ItemAddOnClickListener().onClick(mock())

        // Assert
        verify(game).addItemToInventory("myItem")
        verify(inventoryPanel).clear()
        verify(inventoryPanel).update()
    }

    @Test
    fun onClick_noText_noItemAdded() {
        // Arrange
        declareMock<Game>()
        declareMock<InventoryPanel> {
            given(getItem()).willReturn("")
        }

        // Act
        ItemAddOnClickListener().onClick(mock())

        // Assert
        verifyZeroInteractions(game)
    }
}


