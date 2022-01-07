package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DisplayWayPointsCommand : KoinComponent {

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    fun execute(game: Game): String {
        val wayPoints = game.displayWayPoints()
        genericCommandViewModel.onSearchResultChange(wayPoints)
        return ""
    }

}
