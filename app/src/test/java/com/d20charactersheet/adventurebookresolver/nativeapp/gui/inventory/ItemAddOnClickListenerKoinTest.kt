package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.*
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
    private val itemPanel: ItemPanel by inject()

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
        declareMock<ItemPanel> {
            whenever(getItem()).doReturn("myItem")
        }

        // Act
        ItemAddOnClickListener().onClick(mock())

        // Assert
        verify(game).addItemToInventory("myItem")
        verify(itemPanel).clear()
        verify(itemPanel).update()
    }

    @Test
    fun onClick_noText_noItemAdded() {
        // Arrange
        declareMock<Game>()
        declareMock<ItemPanel> {
            whenever(getItem()).doReturn("")
        }

        // Act
        ItemAddOnClickListener().onClick(mock())

        // Assert
        verifyZeroInteractions(game)
    }
}


