package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class MainActivityKoinTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onOptionsItemSelected_restart() {
        // Arrange
        val menuItem: MenuItem = mock {
            on { itemId } doReturn (R.id.option_restart)
        }
        val restartDialog: AlertDialog = mock()
        val underTest = MainActivity()
        underTest.restartDialog = mock {
            on { create(underTest) } doReturn restartDialog
        }

        // Act
        val result = underTest.onOptionsItemSelected(menuItem)

        // Assert
        assertThat(result).isTrue()
        verify(restartDialog).show()
    }

    @Test
    fun onOptionsItemSelected_save() {
        // Arrange
        val menuItem: MenuItem = mock {
            on { itemId } doReturn (R.id.option_save)
        }

        // Act
        val result = MainActivity().onOptionsItemSelected(menuItem)

        // Assert
        assertThat(result).isTrue()
        verify(game).saveBook()
    }

    @Test
    fun onOptionsItemSelected_nothing() {
        // Arrange
        val menuItem: MenuItem = mock {
            on { itemId } doReturn (-1)
        }

        // Act
        val result = MainActivity().onOptionsItemSelected(menuItem)

        // Assert
        assertThat(result).isFalse()
        verifyZeroInteractions(game)
    }

}