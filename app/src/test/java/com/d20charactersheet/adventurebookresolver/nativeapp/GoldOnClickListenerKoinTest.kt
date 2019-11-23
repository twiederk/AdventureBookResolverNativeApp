package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class GoldOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val goldPanel: GoldPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        declareMock<GoldPanel>()
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `GoldIncreaseOnClickListener onClick`() {
        // Arrange

        // Act
        GoldIncreaseOnClickListener().onClick(mock())

        // Assert
        verify(game).increaseGold()
        verify(goldPanel).update()
    }

    @Test
    fun `GoldDecreaseOnClickListener onClick`() {
        // Arrange

        // Act
        GoldDecreaseOnClickListener().onClick(mock())

        // Assert
        verify(game).decreaseGold()
        verify(goldPanel).update()
    }

}


