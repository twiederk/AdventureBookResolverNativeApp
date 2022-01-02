package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchCommandKoinTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }
    private val genericCommandViewModel: GenericCommandViewModel by inject()

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
    fun execute() {
        // arrange
        val game: Game = mock()
        val expectedSearchResult = listOf(BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure"))
        whenever(game.search("start")).doReturn(expectedSearchResult)

        // act
        val searchResult = SearchCommand().execute(game, "start")

        // assert
        assertThat(searchResult).isEmpty()
        verify(game).search("start")
        verify(genericCommandViewModel).onSearchResultChange(expectedSearchResult)
    }

}