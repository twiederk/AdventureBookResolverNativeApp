package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

internal class ProvisionsPanelKoinTest : KoinTest {

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
    fun create() {
        // Arrange
        val provisionsValueTextView: TextView = mock()
        val provisionsPlusButton: Button = mock()
        val provisionsMinusButton: Button = mock()
        val provisionsEatButton: Button = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.provisions_value_text_view) } doReturn provisionsValueTextView
            on { findViewById<TextView>(R.id.provisions_plus_button) } doReturn provisionsPlusButton
            on { findViewById<TextView>(R.id.provisions_minus_button) } doReturn provisionsMinusButton
            on { findViewById<TextView>(R.id.provisions_eat_button) } doReturn provisionsEatButton
        }
        val underTest =
            ProvisionsPanel(mock())

        // Act
        underTest.create(rootView)

        // Assert
        assertThat(underTest.provisionsValueTextView).isSameAs(provisionsValueTextView)
        argumentCaptor<ProvisionsIncreaseOnClickListener> {
            verify(provisionsPlusButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ProvisionsIncreaseOnClickListener::class.java)
        }
        argumentCaptor<ProvisionsDecreaseOnClickListener> {
            verify(provisionsMinusButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ProvisionsDecreaseOnClickListener::class.java)
        }
        argumentCaptor<ProvisionsEatOnClickListener> {
            verify(provisionsEatButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ProvisionsEatOnClickListener::class.java)
        }
    }

    @Test
    fun update() {
        // Arrange
        val game: Game = mock {
            on { getProvisions() } doReturn "4"
            on { hasProvisions() } doReturn true
        }
        val underTest =
            ProvisionsPanel(game)
        underTest.provisionsValueTextView = mock()
        underTest.provisionsEatButton = mock()

        // Act
        underTest.update()

        // Assert
        verify(game).getProvisions()
        verify(underTest.provisionsValueTextView).text = "4"
        verify(underTest.provisionsEatButton).isEnabled = true
    }

}