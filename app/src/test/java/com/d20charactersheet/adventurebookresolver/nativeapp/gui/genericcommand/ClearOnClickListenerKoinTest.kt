package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
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

class ClearOnClickListenerKoinTest : KoinTest {

    private val genericCommandPanel: GenericCommandPanel by inject()

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
    fun `clear button onClick clears output area`() {
        // Arrange
        declareMock<GenericCommandPanel>()

        // Act
        ClearOnClickListener()
            .onClick(mock())

        // Assert
        verify(genericCommandPanel).clearOutput()
    }

}