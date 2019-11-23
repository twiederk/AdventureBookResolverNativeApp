package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.TextView
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

internal class GoldPanelKoinTest : KoinTest {

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
        val goldValueTextView: TextView = mock()
        val goldPlusButton: Button = mock()
        val goldMinusButton: Button = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.gold_value_text_view) } doReturn goldValueTextView
            on { findViewById<TextView>(R.id.gold_plus_button) } doReturn goldPlusButton
            on { findViewById<TextView>(R.id.gold_minus_button) } doReturn goldMinusButton
        }
        val underTest = GoldPanel(mock())

        // Act
        underTest.create(rootView)

        // Assert
        assertThat(underTest.goldValueTextView).isSameAs(goldValueTextView)
        argumentCaptor<GoldIncreaseOnClickListener> {
            verify(goldPlusButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(GoldIncreaseOnClickListener::class.java)
        }
        argumentCaptor<GoldDecreaseOnClickListener> {
            verify(goldMinusButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(GoldDecreaseOnClickListener::class.java)
        }
    }

    @Test
    fun update() {
        // Arrange
        val game: Game = mock {
            on { getGold() } doReturn "5"
        }
        val underTest = GoldPanel(game)
        underTest.goldValueTextView = mock()

        // Act
        underTest.update()

        // Assert
        verify(game).getGold()
        verify(underTest.goldValueTextView).text = "5"
    }

}