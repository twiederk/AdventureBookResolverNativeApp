package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.lifecycle.MutableLiveData
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GenericCommandPanelKoinTest : KoinTest {

    private val underTest: GenericCommandPanel by inject()
    private val genericCommandViewModel: GenericCommandViewModel by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<GenericCommandViewModel>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `get selected command`() {
        // Arrange
        underTest.commandSpinner = mock {
            on { selectedItem } doReturn Command.Create
        }

        // Act
        val selectedCommand = underTest.getSelectedCommand()

        // Assert
        assertThat(selectedCommand).isSameAs(Command.Create)
    }

    @Test
    fun `get argument`() {
        // Arrange
        whenever(genericCommandViewModel.argument).thenReturn(MutableLiveData("myArgument"))

        // Act
        val argument = underTest.getArgument()

        // Assert
        assertThat(argument).isEqualTo("myArgument")
    }

    @Test
    fun `append output`() {
        // Arrange
        underTest.outputTextView = mock()

        // Act
        underTest.appendOutput("myOutput")

        // Assert
        verify(underTest.outputTextView).append("myOutput\n")
    }

    @Test
    fun `clear output`() {
        // Arrange
        underTest.outputTextView = mock()

        // Act
        underTest.clearOutput()

        // Assert
        verify(underTest.outputTextView).text = ""
        verify(genericCommandViewModel).onBookEntryListChange(emptyList())
        verify(genericCommandViewModel).onActionListChange(emptyList())
        verify(genericCommandViewModel).onMaxCombinationsChange(0)
        verify(genericCommandViewModel).onSolutionListChange(emptyList())
        verify(genericCommandViewModel).onOutputTextChange("")
    }

}