package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
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

class SaveOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

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
    fun onClick() {
        // arrange
        val messageDisplay: MessageDisplay = mock()
        val view: View = mock()
        val book = AdventureBook("myTitle")
        whenever(game.book).doReturn(book)


        // act
        SaveOnClickListener(messageDisplay).onClick(view)

        // assert
        verify(game).saveBook()
        verify(messageDisplay).display(view, "Saved: myTitle")
    }

}
