package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SolveCommand : KoinComponent {

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    fun execute(game: Game): String = try {
        val solutions = game.solve(ComposeBookSolverListener(genericCommandViewModel))
        genericCommandViewModel.onSolutionListChange(solutions)
        ""
    } catch (throwable: Throwable) {
        val message = throwable.localizedMessage ?: "Exception without message"
        genericCommandViewModel.onOutputTextChange(message)
        ""
    }

}
