package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.Attribute
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.core.domain.Attributes
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class GenericCommandFragmentKoinTest : KoinTest {

    private val underTest = GenericCommandFragment()
    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }

        val attributes = Attributes(
            strength = Attribute(AttributeName.STRENGTH, 14, 20),
            dexterity = Attribute(AttributeName.DEXTERITY, 8, 9),
            luck = Attribute(AttributeName.LUCK, 5, 10)
        )
        game.book = AdventureBook(attributes = attributes)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onCreate() {
        // Arrange
        val container = mock<ViewGroup>()
        val commandSpinner = mock<Spinner>()
        val argumentEditText = mock<EditText>()
        val executeButton = mock<Button>()
        val clearButton = mock<Button>()
        val outputTextView = mock<TextView>()
        val rootView = mock<View> {
            on { findViewById<Spinner>(R.id.command_spinner) } doReturn commandSpinner
            on { findViewById<EditText>(R.id.argument_edit_text) } doReturn argumentEditText
            on { findViewById<Button>(R.id.execute_button) } doReturn executeButton
            on { findViewById<Button>(R.id.clear_button) } doReturn clearButton
            on { findViewById<TextView>(R.id.output_text_view) } doReturn outputTextView
        }
        val inflater = mock<LayoutInflater> {
            on { inflate(R.layout.fragment_generic_command, container, false) } doReturn rootView
        }
        val savedInstanceState = mock<Bundle>()

        // Act
        underTest.onCreateView(inflater, container, savedInstanceState)

        // Assert
        verify(inflater).inflate(R.layout.fragment_generic_command, container, false)
        verify(commandSpinner).adapter = any()
        argumentCaptor<ExecuteOnClickListener> {
            verify(executeButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ExecuteOnClickListener::class.java)
        }
        argumentCaptor<ClearOnClickListener> {
            verify(clearButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ClearOnClickListener::class.java)
        }

    }

}