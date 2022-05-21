package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
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
import org.mockito.kotlin.verify

class CreateBookScreenViewModelKoinTest : KoinTest {

    private val createBookScreenViewModel: CreateBookScreenViewModel by inject()
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
    fun `should init with default values`() {
        // assert
        assertThat(createBookScreenViewModel.title).isEmpty()
    }

    @Test
    fun `should update title when title is entered`() {

        // act
        createBookScreenViewModel.onTitleChange("myTitle")

        // assert
        assertThat(createBookScreenViewModel.title).isEqualTo("myTitle")
    }

    @Test
    fun `should create new book with given title`() {
        // arrange
        createBookScreenViewModel.onTitleChange("myTitle")

        // act
        createBookScreenViewModel.onCreateClick()

        // assert
        verify(game).createBook("myTitle")
    }

}