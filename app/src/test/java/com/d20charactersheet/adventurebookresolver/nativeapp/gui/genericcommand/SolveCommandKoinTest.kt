package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.core.domain.Combination
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
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
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SolveCommandKoinTest : KoinTest {

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
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `should start solve method and send solution to view model`() {
        // arrange
        val solutions = listOf(Solution(Combination(arrayOf())))
        val game: Game = mock()
        whenever(game.solve(any())).thenReturn(solutions)

        // act
        val result = SolveCommand().execute(game)

        // assert
        assertThat(result).isEmpty()
        verify(game).solve(any())
        assertThat(genericCommandViewModel.solutionList).isEqualTo(solutions)
    }

    @Test
    fun `should send error message to view model when OutOfMemoryError is thrown`() {
        // arrange
        val game: Game = mock()
        whenever(game.solve(any())).doThrow(OutOfMemoryError("Out of memory"))

        // act
        val result = SolveCommand().execute(game)

        // assert
        assertThat(result).isEmpty()
        assertThat(genericCommandViewModel.outputText).isEqualTo("Out of memory")
    }

}