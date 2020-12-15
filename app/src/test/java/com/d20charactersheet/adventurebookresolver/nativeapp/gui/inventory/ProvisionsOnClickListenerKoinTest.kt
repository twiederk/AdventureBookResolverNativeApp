package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
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

class ProvisionsOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val provisionsPanel: ProvisionsPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        declareMock<ProvisionsPanel>()
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `ProvisionsIncreaseOnClickListener onClick`() {
        // Act
        ProvisionsIncreaseOnClickListener()
            .onClick(mock())

        // Assert
        verify(game).increaseProvisions()
        verify(provisionsPanel).update()
    }

    @Test
    fun `ProvisionsDecreaseOnClickListener onClick`() {
        // Act
        ProvisionsDecreaseOnClickListener()
            .onClick(mock())

        // Assert
        verify(game).decreaseProvisions()
        verify(provisionsPanel).update()
    }

    @Test
    fun `ProvisionsEatOnClickListener onClick`() {
        // Act
        ProvisionsEatOnClickListener()
            .onClick(mock())

        // Assert
        verify(game).eatProvision()
        verify(provisionsPanel).update()

    }

}


