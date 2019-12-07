package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
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

class AttributeOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val attributesPanel: AttributesPanel by inject()

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
    fun `onClick increase attribute`() {
        // Arrange
        declareMock<Game>()
        declareMock<AttributesPanel>()

        // Act
        AttributeIncreaseOnClickListener(
            AttributeName.STRENGTH
        ).onClick(mock())

        // Assert
        verify(game).increaseAttribute(AttributeName.STRENGTH, 1)
        verify(attributesPanel).update()
    }

    @Test
    fun `onClick decrease attribute`() {
        // Arrange
        declareMock<Game>()
        declareMock<AttributesPanel>()

        // Act
        AttributeDecreaseOnClickListener(
            AttributeName.STRENGTH
        ).onClick(mock())

        // Assert
        verify(game).decreaseAttribute(AttributeName.STRENGTH, 1)
        verify(attributesPanel).update()
    }


}