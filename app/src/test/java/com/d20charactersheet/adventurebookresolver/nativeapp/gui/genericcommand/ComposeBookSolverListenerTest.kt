package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.Logger
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class ComposeBookSolverListenerTest {

    private val genericCommandViewModel: GenericCommandViewModel = mock()
    private val logger: Logger = mock()
    private val composeBookSolverListener = ComposeBookSolverListener(genericCommandViewModel, logger)


    @Test
    fun `should write time of calculation to log`() {

        // act
        composeBookSolverListener.beginCalculation(LocalDateTime.of(2022, 3, 29, 6, 15))

        // assert
        verify(logger).info("Begin of calculation: 06:15 29.03.2022")
    }

    @Test
    fun `should write end and duration of calculation to log`() {
        // arrange
        composeBookSolverListener.beginCalculation(LocalDateTime.of(2022, 3, 29, 6, 15))

        // act
        composeBookSolverListener.endCalculation(LocalDateTime.of(2022, 3, 29, 6, 20))

        // assert
        verify(logger).info("End of calculation: 06:20 29.03.2022")
        verify(logger).info("Duration: 05:00")
    }

    @Test
    fun `should update number of remaining combinations and write it to log when calculateCombinations is fired`() {

        // act
        composeBookSolverListener.calculateCombinations(10)

        // assert
        verify(genericCommandViewModel).onRemainingCombinationsChange(10)
    }

    @Test
    fun `should update max combinations in GenericCommandViewModel`() {

        // act
        composeBookSolverListener.maxCombinations(100)

        // assert
        verify(genericCommandViewModel).onMaxCombinationsChange(100)
    }

    @Test
    fun `should update number of solutions`() {

        // act
        composeBookSolverListener.foundSolution(1)

        // assert
        verify(genericCommandViewModel).onSolutionFoundChange(1)
    }

}