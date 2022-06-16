package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.BookSolverListener
import com.d20charactersheet.adventurebookresolver.nativeapp.Logger
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class ComposeBookSolverListener(
    private val solutionScreenViewModel: SolutionScreenViewModel,
    private val logger: Logger = Logger
) : BookSolverListener {

    private val formatter = checkNotNull(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))

    private var beginTime: LocalDateTime? = null

    override fun beginCalculation(beginTime: LocalDateTime) {
        this.beginTime = beginTime
        logger.info("Begin of calculation: ${formatter.format(beginTime)}")
    }

    override fun calculateCombinations(numberOfCombinations: Long) {
        solutionScreenViewModel.onRemainingCombinationsChange(numberOfCombinations)
    }

    override fun calculatePath(startEntry: BookEntry, wayPoint: BookEntry, numberOfEntries: Int?) {
        // information is not shown to the user
    }

    override fun endCalculation(endTime: LocalDateTime) {
        logger.info("End of calculation: ${formatter.format(endTime)}")
        val duration = Duration.between(beginTime, endTime)
        logger.info("Duration: ${formatDuration(duration)}")
    }

    override fun foundSolution(numberOfSolutions: Int) {
        solutionScreenViewModel.onSolutionFoundChange(numberOfSolutions)
    }

    override fun maxCombinations(maxCombinations: Long) {
        solutionScreenViewModel.onMaxCombinationsChange(maxCombinations)
    }

    private fun formatDuration(duration: Duration): String {
        val minutes = abs(duration.seconds) % 3600 / 60
        val seconds = abs(duration.seconds) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

}